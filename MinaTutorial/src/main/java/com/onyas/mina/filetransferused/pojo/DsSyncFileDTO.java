package com.onyas.mina.filetransferused.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 同步文件类
 * 
 * @description:
 *
 */
public class DsSyncFileDTO implements Serializable {

	private static final long serialVersionUID = 5082389474270262L;

	private String id;
	private int syncStatus;// 同步状态 0:未同步1：在队列中,2:正在同步3：同步成功,9：同步失败
	private int retryCount; // 重试次数
	private Date finalRetryTime;
	private String syncType;// 同步类型 1：立即发送， 2调度发送
	private String priorityLevel;// 同步优先级 数字，越大越优先
	private int fileGetNum;// 每次获取文件记录数量
	private String documentid;// 文档ID，就是内容库中的documentum_id
	private String versionid;// 文档版本ID
	private String fileName;// 文件名，对应内容库中的object_name
	private String filePath;// 文件路径，文档在内容库中的文件路径
	private long fileSize;// 文件大小（单位：byte）
	private String pointCode;// 目标部署点code
	private String sysCode;// 源业务系统编码
	private String toSysCode;// 目标业务系统编码
	private Date taskCreateTime;// 任务创建时间
	private Date taskStartTime;// 任务开始时间
	private Date taskEndTime;// 任务结束时间
	private String aclName;// ACL权限
	private String taskCycle;// 任务周期
	private int taskType;// 任务类型：0纵向交换，1分发，2共享
	private String executeIp = "0";// 执行本条任务的纵向交换服务所在服务器IP地址

	public static String imme = "1";// 立即发送
	public static String schedule = "2";// 调度发送

	// 同步状态：0未同步1在队列中,2正在同步,3同步成功,9同步失败
	public static int nonSync = 0;// 未同步
	public static int quequeSync = 1;// 在队列中
	public static int syncing = 2;// 正在同步
	public static int syncSucc = 3;// 同步成功
	public static int syncFail = 9;// 同步失败

	private long syncTimeoutMper;// 纵向交换每M传输超时时间，单位：秒
	private String ids;//
	private String exe_info;//
	private boolean notifyFlag;// 是否换醒过;

	private String srcip;
	private String desip;
	private int port;
	
	
	public String getExe_info() {
		return exe_info;
	}

	public void setExe_info(String exeInfo) {
		exe_info = exeInfo;
	}

	public long getSyncTimeoutMper() {
		return syncTimeoutMper;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setSyncTimeoutMper(long syncTimeoutMper) {
		this.syncTimeoutMper = syncTimeoutMper;
	}

	public static int getSyncFail() {
		return syncFail;
	}

	public static void setSyncFail(int syncFail) {
		DsSyncFileDTO.syncFail = syncFail;
	}

	public static int getNonSync() {
		return nonSync;
	}

	public static void setNonSync(int nonSync) {
		DsSyncFileDTO.nonSync = nonSync;
	}

	public static int getSyncing() {
		return syncing;
	}

	public static void setSyncing(int syncing) {
		DsSyncFileDTO.syncing = syncing;
	}

	public static int getSyncSucc() {
		return syncSucc;
	}

	public static void setSyncSucc(int syncSucc) {
		DsSyncFileDTO.syncSucc = syncSucc;
	}

	public String getDocumentid() {
		return documentid;
	}

	public void setDocumentid(String documentid) {
		this.documentid = documentid;
	}

	public String getVersionid() {
		return versionid;
	}

	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getPointCode() {
		return pointCode;
	}

	public void setPointCode(String pointCode) {
		this.pointCode = pointCode;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getToSysCode() {
		return toSysCode;
	}

	public void setToSysCode(String toSysCode) {
		this.toSysCode = toSysCode;
	}

	public Date getTaskCreateTime() {
		return taskCreateTime;
	}

	public void setTaskCreateTime(Date taskCreateTime) {
		this.taskCreateTime = taskCreateTime;
	}

	public Date getTaskStartTime() {
		return taskStartTime;
	}

	public void setTaskStartTime(Date taskStartTime) {
		this.taskStartTime = taskStartTime;
	}

	public Date getTaskEndTime() {
		return taskEndTime;
	}

	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

	public String getAclName() {
		return aclName;
	}

	public void setAclName(String aclName) {
		this.aclName = aclName;
	}

	public String getTaskCycle() {
		return taskCycle;
	}

	public void setTaskCycle(String taskCycle) {
		this.taskCycle = taskCycle;
	}

	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	public String getExecuteIp() {
		return executeIp;
	}

	public void setExecuteIp(String executeIp) {
		this.executeIp = executeIp;
	}

	public static String getImme() {
		return imme;
	}

	public static void setImme(String imme) {
		DsSyncFileDTO.imme = imme;
	}

	public static String getSchedule() {
		return schedule;
	}

	public static void setSchedule(String schedule) {
		DsSyncFileDTO.schedule = schedule;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(int syncStatus) {
		this.syncStatus = syncStatus;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public String getSyncType() {
		return syncType;
	}

	public void setSyncType(String syncType) {
		this.syncType = syncType;
	}

	public String getPriorityLevel() {
		return priorityLevel;
	}

	public void setPriorityLevel(String priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

	public Date getFinalRetryTime() {
		return finalRetryTime;
	}

	public void setFinalRetryTime(Date finalRetryTime) {
		this.finalRetryTime = finalRetryTime;
	}

	public int getFileGetNum() {
		return fileGetNum;
	}

	public void setFileGetNum(int fileGetNum) {
		this.fileGetNum = fileGetNum;
	}

	public static int getQuequeSync() {
		return quequeSync;
	}

	public static void setQuequeSync(int quequeSync) {
		DsSyncFileDTO.quequeSync = quequeSync;
	}

	public boolean isNotifyFlag() {
		return notifyFlag;
	}

	public void setNotifyFlag(boolean notifyFlag) {
		this.notifyFlag = notifyFlag;
	}


	public String getSrcip() {
		return srcip;
	}

	public void setSrcip(String srcip) {
		this.srcip = srcip;
	}

	public String getDesip() {
		return desip;
	}

	public void setDesip(String desip) {
		this.desip = desip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	
	
}
