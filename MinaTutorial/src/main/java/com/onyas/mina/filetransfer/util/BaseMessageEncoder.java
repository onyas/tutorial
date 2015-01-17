package com.onyas.mina.filetransfer.util;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

import com.onyas.mina.filetransfer.bean.BaseMessage;
import com.onyas.mina.filetransfer.bean.FileBean;

public class BaseMessageEncoder implements MessageEncoder<BaseMessage> {

	/**
	 * 基本信息编码
	 * */
	public void encode(IoSession session, BaseMessage message,ProtocolEncoderOutput outPut) throws Exception {
		// TODO Auto-generated method stub
		IoBuffer buffer = IoBuffer.allocate(1024*1024*50); 
		buffer.putInt(message.getDataType());
		FileBean bean = (FileBean) message.getData();
		byte[] byteStr = bean.getFileName().getBytes(BeanUtil.charset);
		buffer.putInt(byteStr.length);
		buffer.putInt(bean.getFileSize());
		buffer.put(byteStr);
		buffer.put(bean.getFileContent());
		buffer.flip();
		outPut.write(buffer);
		System.out.println("编码完成！");
	}

}
