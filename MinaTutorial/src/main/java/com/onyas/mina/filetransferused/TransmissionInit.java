package com.onyas.mina.filetransferused;


import org.apache.log4j.Logger;

import com.onyas.mina.filetransferused.server.FileServer;

/**
 * 文件分发模块入口类，在这里启动服务端，并初始化线程池
 * @author Administrator
 *
 */
public class TransmissionInit {
	private static Logger logger = Logger.getLogger(TransmissionInit.class);
	
	public static void main(String[] args) {
		FileServer fileServer = new FileServer();
		fileServer.start();
		logger.info("文件分发模块已经启动成功！！！");
	}
	
}
