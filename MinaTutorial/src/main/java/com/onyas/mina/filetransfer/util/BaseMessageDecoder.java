package com.onyas.mina.filetransfer.util;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

import com.onyas.mina.filetransfer.bean.BaseMessage;
import com.onyas.mina.filetransfer.bean.FileBean;

public class BaseMessageDecoder  implements MessageDecoder {
	private AttributeKey CONTEXT = new AttributeKey(getClass(), "context");
	/**
	 * 判断是否能解码
	 * */
	public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
		Context context = (Context) session.getAttribute(CONTEXT);
		if(context == null){
			context = new Context();
			//解码格式为 ：业务类型(传送文件)+文件名字的长度+文件长度+文件名字+文件内容
			context.dataType = in.getInt();//业务类型
			if(context.dataType == BeanUtil.UPLOAD_FILE){
				context.strLength = in.getInt();//文件名字的长度
				context.byteStr = new byte[context.strLength];
				context.fileSize = in.getInt();//文件长度
				context.byteFile = new byte[context.fileSize];
				session.setAttribute(CONTEXT, context);
				return MessageDecoderResult.OK;
			}else{
				return MessageDecoderResult.NOT_OK;
			}
		}else{
			if(context.dataType == BeanUtil.UPLOAD_FILE){
				return MessageDecoderResult.OK;
			}else{
				return MessageDecoderResult.NOT_OK;
			}
		}
	}

	/**
	 * 开始解码
	 * */
	public MessageDecoderResult decode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput outPut) throws Exception {
		System.out.println("开始解码");
		Context context = (Context) session.getAttribute(CONTEXT);
		if(!context.init){
			context.init = true;
			in.getInt();
			in.getInt();
			in.getInt();
		}
		byte[] byteFile = context.byteFile;
		int count = context.count;
		while(in.hasRemaining()){
			byte b = in.get();
			if(!context.hasReadName){//判断是否读取了文件名字
				context.byteStr[count] = b;
				if(count == context.strLength-1){
					context.fileName = new String(context.byteStr,BeanUtil.charset);
					System.out.println(context.fileName);
					count = -1;
					context.hasReadName = true;
				}
			}
			if(context.hasReadName && count != -1){//第二次读取文件内容
				byteFile[count] = b;
			}
		//	byteFile[count] = b;
			count++;
		}
		context.count = count;
		System.out.println("count:"+count);
		System.out.println("context.fileSize:"+context.fileSize);
		session.setAttribute(CONTEXT, context);
		if(context.count == context.fileSize){
			BaseMessage message = new BaseMessage();
			message.setDataType(context.dataType);
			FileBean bean = new FileBean();
			bean.setFileName(context.fileName);
			bean.setFileSize(context.fileSize);
			bean.setFileContent(context.byteFile);
			message.setData(bean);
			outPut.write(message);
			context.reset();
		}
		return MessageDecoderResult.OK;
	}

	/**
	 * 
	 * */
	public void finishDecode(IoSession session, ProtocolDecoderOutput outPut)
			throws Exception {
		System.out.println("end:::::::::::::::::");
	}
	private class Context{
		public int dataType;//业务类型(传送文件)
		public int fileSize;//文件大小
		public byte[] byteFile;//存放文件内容
		public int count;//用来记录读取的次数，第一次为读取文件名字，第二次为读取文件内容
		public boolean hasReadName;//是否已经读取了文件名字
		public int strLength;//文件名字的长度
		public byte[] byteStr;//存放文件名字
		public String fileName;//文件名字
		public boolean init = false;
		
		public void reset(){
			dataType = 0;
			byteFile = null;
			count = 0;
			strLength = 0;
			hasReadName = false;
			fileSize = 0;
			byteStr = null;
			fileName = null;
			
		}
	}


}
