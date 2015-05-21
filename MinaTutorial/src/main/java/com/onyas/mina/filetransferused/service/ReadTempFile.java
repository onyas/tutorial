package com.onyas.mina.filetransferused.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.onyas.mina.filetransferused.helper.Constant;
import com.onyas.mina.filetransferused.helper.Util;
import com.onyas.mina.filetransferused.message.SendFileMessage;

public class ReadTempFile implements Runnable {

	private static final Logger logger = Logger.getLogger(ReadTempFile.class);
	public static final int BUFFER_SIZE = 1024*10;

	private File file;

	private SendFileMessage sendFileMsg;

	private IoSession session;

	public ReadTempFile() {
	}

	public ReadTempFile(File file, SendFileMessage sendFileMsg,
			IoSession session) {
		this.file = file;
		this.sendFileMsg = sendFileMsg;
		this.session = session;
	}

	public void run() {
		OutputStream out = (OutputStream) session.getAttribute(Constant.KEY_OUT);
		Long startPos = sendFileMsg.getFileLength();
		RandomAccessFile rafile = null;
		try {
			logger.info("客户端开始向session中写入数据");
			rafile = new RandomAccessFile(file, "r");
//			rafile.seek(startPos);
			rafile.seek(0);
			byte[] buf = new byte[BUFFER_SIZE];
			int realReadSize = -1;
			while ((realReadSize = rafile.read(buf)) != -1) {
				out.write(buf, 0, realReadSize);
				out.flush();
			}
			session = Util.addFilter(session);
			logger.info("客户端完成向session中写入数据");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			session.close(true);
		} finally {
			try {
				if (file.exists()) {
					rafile.close();
//					file.delete();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}
