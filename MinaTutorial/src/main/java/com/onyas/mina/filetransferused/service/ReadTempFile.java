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

	Logger logger = Logger.getLogger(ReadTempFile.class);

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
		OutputStream out = (OutputStream) session
				.getAttribute(Constant.KEY_OUT);
		Long startPos = sendFileMsg.getFileLength();
		RandomAccessFile rafile = null;
		try {
			logger.info("Write file to session.");
			rafile = new RandomAccessFile(file, "r");
			rafile.seek(startPos);
			byte[] buf = new byte[1024];
			int realReadSize = -1;
			while ((realReadSize = rafile.read(buf)) != -1) {
				out.write(buf, 0, realReadSize);
				out.flush();
			}
			Util.addFilter(session);
			logger.info("Write file to session complete!");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			session.close(true);
		} finally {
			try {
				if (file.exists()) {
					rafile.close();
					file.delete();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}
