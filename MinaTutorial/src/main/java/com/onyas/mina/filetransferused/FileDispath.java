package com.onyas.mina.filetransferused;

import java.io.File;

/**
 * 文件分发类
 * @author Administrator
 *
 */
public class FileDispath {

	/**
	 * 
	 * @param file 目标文件 
	 * @param hostname 目标主机
	 * @param port 目标端口
	 */
	public void dispath2Server(File file,String hostname,int port){
		//TODO 开始分发文件
	}
	
	
	public static void main(String[] args) {
		FileDispath fileDispath = new FileDispath();
		File file = new File("c:\\1.txt");
		String hostname = "127.0.0.1";
		int port = 7654;
		fileDispath.dispath2Server(file, hostname, port);
	}
}
