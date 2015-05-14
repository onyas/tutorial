package com.onyas.mina.filetransferused.helper;

public class CommandList {

	public static String  SENDFILE = "sendFile";
	public static String  RETURN_STATUS = "returnStatus";
	public static String  IMME_SENDFILE = "immeSendFile";
	public static String  REFRESH_CONFIG = "refreshConfig";
	public static String  REMOTE_PROXY = "remoteProxy";
	public static String  REMOTE_EXECUTE = "remoteExecute";
	public static String  DOWNLOAD_READY = "downloadReady";// 准备发送
	public static String  DOWNLOAD_LOCAL = "downloadLocal";// 文件已经下载本地
	public static String  DOWNLOAD_SENDFILE = "downloadSendfile";// 文件发送
	public static String  DOWNLOAD_END = "downloadEnd";// 结束发送
	public static String  SENDSEARCH_INFO = "sendSearchInfo";// 发送基本信息
	public static String  SENDSEARCH_FILE = "sendSearchFile";// 发送文件信息
	public static String  INFO = "sendCommandInfo";// 通用通道发送基本信息
	public static String  FILE = "sendCommandFile";// 通用通道发送文件信息
	public static String  INFO_File = "sendCommandInfoFile";// 通用通道发送基本信息和文件信息

}