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
	 * �Ƿ��ʺϽ���
	 * */
	public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
		// TODO Auto-generated method stub
		Context context = (Context) session.getAttribute(CONTEXT);
		if(context == null){
			context = new Context();
			context.dataType = in.getInt();
			if(context.dataType == BeanUtil.UPLOAD_FILE){
				context.strLength = in.getInt();
				context.byteStr = new byte[context.strLength];
				context.fileSize = in.getInt();
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
	 * ��ݽ���
	 * */
	public MessageDecoderResult decode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput outPut) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("��ʼ���룺");
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
			if(!context.isReadName){
				context.byteStr[count] = b;
				if(count == context.strLength-1){
					context.fileName = new String(context.byteStr,BeanUtil.charset);
					System.out.println(context.fileName);
					count = -1;
					context.isReadName = true;
				}
			}
			if(context.isReadName && count != -1){
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
		// TODO Auto-generated method stub
		System.out.println("end:::::::::::::::::");
	}
	private class Context{
		public int dataType;
		public byte[] byteFile;
		public int count;
		public int strLength;
		public boolean isReadName;
		public int fileSize;
		public byte[] byteStr;
		public String fileName;
		public boolean init = false;
		
		public void reset(){
			dataType = 0;
			byteFile = null;
			count = 0;
			strLength = 0;
			isReadName = false;
			fileSize = 0;
			byteStr = null;
			fileName = null;
			
		}
	}


}
