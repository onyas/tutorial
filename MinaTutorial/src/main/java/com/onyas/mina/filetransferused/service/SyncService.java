package com.onyas.mina.filetransferused.service;

import java.io.File;

import com.onyas.mina.filetransferused.pojo.DsSyncFileDTO;

public interface SyncService {

	public void uploadFile(File file)throws Exception;

	public void setIp(String ip);

	public void setPort(int port);

	public void setContext(String context);

	public void setTransmissionTask(TransmissionTask task);

	public void sendSearchInfo(DsSyncFileDTO dsSyncFileDTO);

	public void downloadSync(DsSyncFileDTO dsSyncFileDTO);

	public void commondSendInfo(DsSyncFileDTO dsSyncFileDTO) throws Exception;

}