package com.onyas.mina.filetransferused.service;

public abstract class AbstractSyncService implements SyncService {

	private String ip;
	private int port;
	private String context;

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	protected abstract int checkFileisNotExist(String fileContext);

}
