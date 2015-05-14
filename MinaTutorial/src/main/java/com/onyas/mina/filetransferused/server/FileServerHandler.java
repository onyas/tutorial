package com.onyas.mina.filetransferused.server;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.onyas.mina.filetransferused.helper.CommandList;
import com.onyas.mina.filetransferused.message.SendFileMessage;

public class FileServerHandler implements IoHandler {

	private static Logger logger = Logger.getLogger(FileServerHandler.class);
	
	public void sessionCreated(IoSession session) throws Exception {
		logger.info("客户端连接："+session.getRemoteAddress());
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
		logger.error("Server socket exception,close socket.");
		logger.error(cause.getMessage());
		session.close(true);
	}

	public void messageReceived(IoSession session, Object message)
			throws Exception {
		logger.info("服务器收到消息..");
		
		if(message instanceof SendFileMessage){//如果是分发文件
			SendFileMessage sendFileMsg = (SendFileMessage) message;
			String command = sendFileMsg.getCommand();
			if(command.equals(CommandList.SENDFILE)){//分发文件的命令
				//TODO 分发文件
			}
		}
	}

	public void messageSent(IoSession session, Object message) throws Exception {
		
	}

}
