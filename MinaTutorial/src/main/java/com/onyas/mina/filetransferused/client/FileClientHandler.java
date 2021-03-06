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
import com.onyas.mina.filetransferused.service.ThreadPoolService;

public class FileClientHandler extends StreamIoHandler {
	private static Logger logger = Logger.getLogger(FileClientHandler.class);
	
	private ThreadPoolService threadPoolService;

	@Override
	public void sessionCreated(IoSession session)   {
		threadPoolService = ThreadPoolService.getInstance();
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
			SendFileMessage sendFileMsg = (SendFileMessage)message;
            File file = (File)session.getAttribute(Constant.KEY_FILE);
            String command = sendFileMsg.getCommand();
            int answer = sendFileMsg.getAnswer();
            if(command.equals(CommandList.SENDFILE)){
                logger.info("Check file exist return status(0-not exist,1-has existed,2-exception) is ["+answer+"]");
                //文件不存在，需要上传
                if(answer == 0){
                	Util.delFilter(session);
                    readFile(session, sendFileMsg);
                //文件已存在，不需上传
                }else if(answer == 1){
                    logger.info("There has the same file in documentum.");
                    session.close(true);
                //检查文档存在发生异常
                }else{
                    logger.info("Check file exist exception.");
                    session.close(true);
                }
            }else if(command.equals(CommandList.RETURN_STATUS)){
                //文件接收入库成功，在这里做状态更新
                if(answer == 0){
                	logger.info("文档分发成功");
                }else{//文件接收端异常
                	logger.info("文档分发失败");
                }
                session.close(true);
//                if(file.exists()){
//                    file.delete();
//                }
            }
		}else{
            super.messageReceived(session, message);
        }
	}

	private void readFile(IoSession session, SendFileMessage info) {
		  File file = (File)session.getAttribute(Constant.KEY_FILE);
//		  new Thread (new ReadTempFile(file, info, session)).start();
		  threadPoolService.execute(new ReadTempFile(file, info, session));
	}

	@Override
	public void messageSent(IoSession session, Object message)   {

	}

	@Override
	protected void processStreamIo(IoSession session, InputStream in,
			OutputStream out) {
	}

}
