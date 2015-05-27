package com.onyas.thrift.mongodbused.common;

public class UdsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String massageId;

	public UdsException() {
    }

    public UdsException(String msg) {
        super(msg);
    }
    
    public UdsException(String msg, Throwable t) {
        super(msg, t);
    }
    
    public UdsException(String msgId, String msg, Throwable t) {
    	super(msg, t);
    	this.massageId = msgId;        
    }
    
    public String getMassageId() {
    	return this.massageId;
    }
}