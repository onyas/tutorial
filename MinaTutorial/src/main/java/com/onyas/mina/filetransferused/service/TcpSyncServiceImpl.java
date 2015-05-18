package com.onyas.mina.filetransferused.service;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.onyas.mina.filetransferused.client.FileClient;
import com.onyas.mina.filetransferused.helper.CommandList;
import com.onyas.mina.filetransferused.message.SendFileMessage;
import com.onyas.mina.filetransferused.pojo.DsSyncFileDTO;

public class TcpSyncServiceImpl extends AbstractSyncService implements
		SyncService {
	
	Logger logger = Logger.getLogger(TcpSyncServiceImpl.class);

	public void uploadFile(File file)  throws  Exception{
		SendFileMessage sendFileMsg = new SendFileMessage();
		sendFileMsg.setCommand(CommandList.SENDFILE);
		sendFileMsg.setFileName(file.getName());
		sendFileMsg.setFileLength(file.length());
		IoSession session =FileClient.createSesion(getIp(), getPort());
		if(session == null){
            throw new Exception(" Can not connect mina server on:["+getIp()+":"+getPort()+"]");
        }
        logger.info(" uploadfile server url = " + getIp() + ":" +getPort()+ ",filename=" + file.getName() ) ;
        session.getConfig().setBothIdleTime(5000);
        session.write(sendFileMsg);
	}

	public void sendSearchInfo(DsSyncFileDTO dsSyncFileDTO) {

	}

	public void downloadSync(DsSyncFileDTO dsSyncFileDTO) {

	}

	public void commondSendInfo(DsSyncFileDTO dsSyncFileDTO) throws Exception {

	}

	@Override
	protected int checkFileisNotExist(String fileContext) {
		return 0;
	}

	public void setTransmissionTask(TransmissionTask task) {

	}

}
