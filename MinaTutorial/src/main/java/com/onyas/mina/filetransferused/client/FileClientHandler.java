package com.onyas.mina.filetransferused.client;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.stream.StreamIoHandler;

import com.onyas.mina.filetransferused.helper.CommandList;
import com.onyas.mina.filetransferused.helper.Constant;
import com.onyas.mina.filetransferused.helper.Util;
import com.onyas.mina.filetransferused.message.SendFileMessage;
import com.onyas.mina.filetransferused.service.ReadTempFile;

public class FileClientHandler extends StreamIoHandler {
	private static Logger logger = Logger.getLogger(FileClientHandler.class);

	@Override
	public void sessionCreated(IoSession session)   {

	}

	@Override
	public void sessionClosed(IoSession session)   {

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
		logger.error("Client socket exception,close socket.");
		logger.error(cause.getMessage());
		session.close(true);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			  {
		if(message instanceof SendFileMessage){
			SendFileMessage info = (SendFileMessage)message;
            File file = (File)session.getAttribute(Constant.KEY_FILE);
            String command = info.getCommand();
            int answer = info.getAnswer();
            if(command.equals(CommandList.SENDFILE)){
                logger.info("Check file exist return status(0-not exist,1-has existed,2-exception) is ["+answer+"]");
                //文件不存在，需要上传
                if(answer == 0){
                	Util.delFilter(session);
                    sendFile(session, info);
                //文件已存在，不需上传
                }else if(answer == 1){
                    logger.info("There has the same file in documentum.");
                    session.close(true);
                //检查文档存在发生异常
                }else{
                    logger.info("Check file exist exception.");
                    session.close(true);
                }
            }else if(command.equals(CommandList.SENDSEARCH_FILE)){//send search file
            	sendFile(session, info);
            }else if(command.equals(CommandList.RETURN_STATUS)){
                //文件接收入库成功
                if(answer == 0){
                //文件接收端异常
                }else{
                }
                session.close(true);
                if(file.exists()){
                    file.delete();
                }
            }
		}
	}

	private void sendFile(IoSession session, SendFileMessage info) {
		  File file = (File)session.getAttribute(Constant.KEY_FILE);
//		  TODO 每次都是new一个线程，资源消耗比较大，要改为线程池
		  new Thread (new ReadTempFile(file, info, session)).start();
	}

	@Override
	public void messageSent(IoSession session, Object message)   {

	}

	@Override
	protected void processStreamIo(IoSession session, InputStream in,
			OutputStream out) {
	}

}
