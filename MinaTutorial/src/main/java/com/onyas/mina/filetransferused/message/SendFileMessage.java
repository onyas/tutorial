package com.onyas.mina.filetransferused.message;

public class SendFileMessage extends BaseMessage {
	
	private static final long serialVersionUID = 4053405168565885588L;

	 
    private String fileName;

    private long fileLength;
    
    private String syncType;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileLength() {
		return fileLength;
	}

	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}

	public String getSyncType() {
		return syncType;
	}

	public void setSyncType(String syncType) {
		this.syncType = syncType;
	}
}
