package com.onyas.mina.filetransferused.service;

import java.io.File;

import com.onyas.mina.filetransferused.pojo.DsSyncFileDTO;

/**
 * 分发任务实际执行类
 * @author Administrator
 *
 */
public class TransmissionTask {

	private DsSyncFileDTO dsSyncFileDTOSend;
	
	private TcpSyncServiceImpl syncServiceImpl;
	
	public TransmissionTask(DsSyncFileDTO dsSyncFileDTOSend) {
		this.dsSyncFileDTOSend = dsSyncFileDTOSend;
	}

	public void execute() {
		syncServiceImpl = new TcpSyncServiceImpl();
		//设置目标主机和端口
		syncServiceImpl.setIp(dsSyncFileDTOSend.getDesip());
		syncServiceImpl.setPort(dsSyncFileDTOSend.getPort());
		
		File file = new File(dsSyncFileDTOSend.getFilePath());
		try {
			syncServiceImpl.uploadFile(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
