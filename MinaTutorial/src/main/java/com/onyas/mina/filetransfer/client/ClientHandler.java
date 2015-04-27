package com.onyas.mina.filetransfer.client;

import java.io.File;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.onyas.mina.filetransfer.bean.BaseMessage;
import com.onyas.mina.filetransfer.bean.FileBean;
import com.onyas.mina.filetransfer.util.BeanUtil;
import com.onyas.mina.filetransfer.util.FileHelper;

public class ClientHandler extends IoHandlerAdapter{
	
	
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		super.messageReceived(session, message);
		
	}

	public void sessionOpened(IoSession session) { 
		BaseMessage baseMessage = new BaseMessage();
		baseMessage.setDataType(BeanUtil.UPLOAD_FILE);
		FileBean bean = new FileBean();
		File file = new File("F:\\Downloads\\BaiduYunGuanjia_5.2.0.exe");
		bean.setFileName(file.getName());
		bean.setFileSize((int)file.length());
		try {
			FileHelper helper =new FileHelper();
			bean.setFileContent(helper.getContent(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
		baseMessage.setData(bean);
		session.write(baseMessage); 
	}

	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
	}
	
	
}
