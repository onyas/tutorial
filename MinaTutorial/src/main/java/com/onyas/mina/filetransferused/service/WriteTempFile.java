package com.onyas.mina.filetransferused.service;

import java.io.DataInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.onyas.mina.filetransferused.helper.CommandList;
import com.onyas.mina.filetransferused.helper.Util;
import com.onyas.mina.filetransferused.message.SendFileMessage;

public class WriteTempFile implements Runnable {
	
	Logger logger = Logger.getLogger(WriteTempFile.class);

	private DataInputStream in;
	
	private String filePath;
	
	private SendFileMessage sendFileMsg;
	
	private IoSession session;
	
	private File file;
	
	private RandomAccessFile randomAccessFile;
	public WriteTempFile() {
	}

	public WriteTempFile(InputStream ins, String filePath,
			SendFileMessage sendFileMsg, IoSession session) {
		this.in = new DataInputStream(ins);
		this.filePath = filePath;
		this.sendFileMsg = sendFileMsg;
		this.session = session;
	}

	public void run() {
		//向客户端反馈接收结果
    	boolean flag=false;
    	SendFileMessage returnInfo = new SendFileMessage();
        returnInfo.setCommand(CommandList.RETURN_STATUS);
        returnInfo.setAnswer(-1);
        
        byte[] bufferByte = new byte[1024];
        int tempData = 0;
        long l = returnInfo.getFileLength();
        logger.info(" Begin write to temp file.");
        try {
            file = new File(filePath);
            randomAccessFile = new RandomAccessFile(file, "rw");
            //断点
            randomAccessFile.seek(sendFileMsg.getFileLength());
            //写入临时文件
            if(file.exists() && l>0 && l==file.length() ){
                logger.info(" File length == breakPoint.");
            }else{
                while ((tempData = in.read(bufferByte)) != -1) {
                    l = l + tempData;
                    randomAccessFile.write(bufferByte, 0, tempData);
                    randomAccessFile.getFD().sync();
                    if(l == sendFileMsg.getFileLength()) break;
                }
            }
            logger.info(" Write to temp file compelete.");
            //判断文件是否接受完毕
            flag= sendFileMsg.getFileLength()==file.length()?true:false;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	if(flag){
	             //设置返回成功状态
	             returnInfo.setAnswer(0);
	             //成功后删除临时文件
//	             if(file!=null&&file.exists()){
//	                 file.delete();
//	             }
	        }
           try {
        	   Util.addFilter(session);
               session.write(returnInfo);
               if(randomAccessFile!=null)randomAccessFile.close();
           } catch (Exception e) {
               logger.error(e.getMessage(), e);
           }
        }
	}

}
