package com.onyas.mina.filetransferused.client;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class FileClientHandler implements IoHandler {
	private static Logger logger = Logger.getLogger(FileClientHandler.class);

	public void sessionCreated(IoSession session) throws Exception {

	}

	public void sessionOpened(IoSession session) throws Exception {

	}

	public void sessionClosed(IoSession session) throws Exception {

	}

	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		logger.error("Client socket timeout,close socket.");
		session.close(true);
	}

	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		logger.error("Client socket exception,close socket.");
		logger.error(cause.getMessage());
		session.close(true);
	}

	public void messageReceived(IoSession session, Object message)
			throws Exception {
		//TODO 开始文件的操作
	}

	public void messageSent(IoSession session, Object message) throws Exception {

	}

}
