package com.onyas.mina.filetransferused.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.stream.StreamIoHandler;

import com.onyas.mina.filetransferused.helper.CommandList;
import com.onyas.mina.filetransferused.helper.Util;
import com.onyas.mina.filetransferused.message.SendCommandMessage;
import com.onyas.mina.filetransferused.message.SendFileMessage;
import com.onyas.mina.filetransferused.service.WriteTempFile;

/**
 * 注意未覆盖sessionOpened方法，因为在里面有初始化session.最好查看父类的sessionOpened
 * @author Administrator
 *
 */
public class FileServerHandler extends StreamIoHandler {

	private static Logger logger = Logger.getLogger(FileServerHandler.class);
	
	@Override
	public void sessionCreated(IoSession session)  {
		logger.info("客户端连接："+session.getRemoteAddress());
	}

	@Override
	public void sessionClosed(IoSession session)  {
		
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			 {
		 logger.error("Client socket timeout,close socket.");
	     session.close(true);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			 {
		logger.error("Server socket exception,close socket.");
		logger.error(cause.getMessage());
		session.close(true);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			 {
		logger.info("服务器收到消息..");
		
		if(message instanceof SendFileMessage){//如果是分发文件
			SendFileMessage sendFileMsg = (SendFileMessage) message;
			String command = sendFileMsg.getCommand();
			if(command.equals(CommandList.SENDFILE)){//分发文件的命令
				Util.delFilter(session);
				receiveFile(session, sendFileMsg);
			}
		}else if(message instanceof SendCommandMessage){//如果是命令
			
		}
	}

	private void receiveFile(IoSession session, SendFileMessage sendFileMsg) {
//		InputStream ins = (InputStream)session.getAttribute(Constant.KEY_IN);
		InputStream ins=null;
		try {
			ins = new FileInputStream(new File(sendFileMsg.getFilePath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//TODO 每次都是new一个线程，资源消耗比较大，要改为线程池
		new Thread(new WriteTempFile(ins, "E:\\1.txt", sendFileMsg, session)).start(); 
	}

	public void messageSent(IoSession session, Object message) {
		
	}

	@Override
	protected void processStreamIo(IoSession session, InputStream in,
			OutputStream out) {
		
	}

}
