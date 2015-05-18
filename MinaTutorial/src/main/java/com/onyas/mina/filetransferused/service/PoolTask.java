package com.onyas.mina.filetransferused.service;

import com.onyas.mina.filetransferused.pojo.DsSyncFileDTO;


public class PoolTask implements Runnable {

	private DsSyncFileDTO dsSyncFileDTOSend;
	
	public PoolTask(DsSyncFileDTO dsSyncFileDTOSend ){
		this.dsSyncFileDTOSend=dsSyncFileDTOSend;
	}
	
	public void run() {
		TransmissionTask task = new TransmissionTask(dsSyncFileDTOSend);
		task.execute();
	}

}
