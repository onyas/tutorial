package com.onyas.mina.filetransfer.server;

import java.io.FileOutputStream;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.onyas.mina.filetransfer.bean.BaseMessage;
import com.onyas.mina.filetransfer.bean.FileBean;

public class ServerHandler extends IoHandlerAdapter{


	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionCreated(session);
	}

	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
	}
	
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		// TODO Auto-generated method stub
		super.messageReceived(session, message);
		System.out.println("==============");
		BaseMessage baseMessage = (BaseMessage) message;
		FileBean bean = (FileBean) baseMessage.getData();
		System.out.println(bean.getFileName());
		FileOutputStream os = new FileOutputStream("f:\\"+bean.getFileName());
		os.write(bean.getFileContent());
		os.close();
	}


	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(session, cause);
	}
}
