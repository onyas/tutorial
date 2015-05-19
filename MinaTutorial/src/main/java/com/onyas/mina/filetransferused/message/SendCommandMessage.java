package com.onyas.mina.filetransferused.message;

public class SendCommandMessage extends BaseMessage{
	public String getExtendsInfo() {
		return extendsInfo;
	}
	public void setExtendsInfo(String extendsInfo) {
		this.extendsInfo = extendsInfo;
	}
	private static final long serialVersionUID = 1L;
    private String taskId;//任务ID
    private String fileName;//文件名字
    private long breakPoint = 0; //断点继传
    private long fileLength;//文件长度
    private String exeInfo;//扩展信息
    private String ids;//回调平台参数
    private String returnInfo;//目标网省保存发送信息返回值
    private String className;//类的ID
    private String extendsInfo;//扩展信息
    private String syncType;//同步类型
    private int taskType;//任务类型
    private String sendInfo;//发送信息
    private int deleteSourceFile;//0不删除1发送成功后删除源文件
    private int deleteTargetFile;//0不删除1发送成功后删除目标网省文件
	public String getTaskId() {
		return taskId;
	}
	public String getFileName() {
		return fileName;
	}
	public long getBreakPoint() {
		return breakPoint;
	}
	public long getFileLength() {
		return fileLength;
	}
	public String getIds() {
		return ids;
	}
	public String getReturnInfo() {
		return returnInfo;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setBreakPoint(long breakPoint) {
		this.breakPoint = breakPoint;
	}
	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public void setReturnInfo(String returnInfo) {
		this.returnInfo = returnInfo;
	}
	public String getExeInfo() {
		return exeInfo;
	}
	public void setExeInfo(String exeInfo) {
		this.exeInfo = exeInfo;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSyncType() {
		return syncType;
	}
	public int getTaskType() {
		return taskType;
	}
	public void setSyncType(String syncType) {
		this.syncType = syncType;
	}
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}
	public String getSendInfo() {
		return sendInfo;
	}
	public void setSendInfo(String sendInfo) {
		this.sendInfo = sendInfo;
	}
	public int getDeleteSourceFile() {
		return deleteSourceFile;
	}
	public int getDeleteTargetFile() {
		return deleteTargetFile;
	}
	public void setDeleteSourceFile(int deleteSourceFile) {
		this.deleteSourceFile = deleteSourceFile;
	}
	public void setDeleteTargetFile(int deleteTargetFile) {
		this.deleteTargetFile = deleteTargetFile;
	}
}
