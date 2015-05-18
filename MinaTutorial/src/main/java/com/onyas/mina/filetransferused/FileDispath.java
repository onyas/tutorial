package com.onyas.mina.filetransferused;

import java.io.File;

import com.onyas.mina.filetransferused.helper.SystemUtil;
import com.onyas.mina.filetransferused.pojo.DsSyncFileDTO;
import com.onyas.mina.filetransferused.service.PoolTask;
import com.onyas.mina.filetransferused.service.ThreadPoolService;

/**
 * 文件分发类，在执行文件分发时，确保文件分发模块已启动成功
 * @author Administrator
 *
 */
public class FileDispath {

	private static ThreadPoolService threadPoolService;
	
	/**
	 * 
	 * 把源文件分发到目标主机
	 * 
	 * @param file 源文件 
	 * @param hostname 目标主机
	 * @param port 目标端口
	 */
	public void dispath2Server(File file,String hostname,int port){
		//开始分发文件
		DsSyncFileDTO dsSyncFileDTOSend = new DsSyncFileDTO();
		dsSyncFileDTOSend.setFileName(file.getName());
		dsSyncFileDTOSend.setFilePath(file.getAbsolutePath());
		dsSyncFileDTOSend.setFileSize(file.length());
		dsSyncFileDTOSend.setSrcip(SystemUtil.getLocalIP());
		dsSyncFileDTOSend.setDesip(hostname);
		dsSyncFileDTOSend.setPort(port);
		threadPoolService.execute(new PoolTask(dsSyncFileDTOSend));
	}
	
	/**
	 * 文件分发类的入口方法
	 * @param args
	 */
	public static void main(String[] args) {
		threadPoolService = ThreadPoolService.getInstance();
		FileDispath fileDispath = new FileDispath();
		File file = new File("c:\\1.txt");
		String hostname = "127.0.0.1";
		int port = 7654;
		fileDispath.dispath2Server(file, hostname, port);
	}
}
