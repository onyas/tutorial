package com.onyas.mina.filetransfer.bean;

public class BaseMessage {
	  private int dataType;		//作为业务判断依据
	  private Object data;		//存储业务数据
	  
	  public BaseMessage(int dataType,Object data){
		  this.dataType = dataType;
		  this.data = data;
	  }
	  public BaseMessage(){
		  
	  }
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	  
	  
	  
	}