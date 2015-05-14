package com.onyas.mina.filetransferused.message;

import java.io.Serializable;

public class BaseMessage implements Serializable{

	private static final long serialVersionUID = 4667607668161929707L;
	
	private String command;//命令
	
	private int answer;//服务器应答

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}
	
	
}
