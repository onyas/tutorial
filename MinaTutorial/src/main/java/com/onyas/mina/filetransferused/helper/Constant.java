package com.onyas.mina.filetransferused.helper;

import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.handler.stream.StreamIoHandler;

public class Constant {

	public final static String SYS_CODE = "sys_code";// 业务编码
	public final static String FROM_SYS_CODE = "from_sys_code";// 源业务编码
	public final static String SOURCE_CODE = "source_code";// 源部署点
	public final static String TASK_START_TIME = "task_start_time";// 业务编码
	public final static String BASIC_METADATA_ATTR = "basic_metadata_attr";// 公共元数据属性

	public final static String BUSINESS_TYPES = "business_types";// 业务类型key

	public final static String TYPE_ATTR = "attr";// 类型属性key

	public final static String TYPE_NAME = "typeName";// 类型名称

	public final static String POINT_IP = "point_ip";// 部署点IP

	public final static String POINT_PORT = "point_port";// 部署点端口

	public final static String POINT_CODE = "point_code";// 部署点编码

	public final static String POINT_CONTEXT = "point_context";// 部署点上下文路径

	public final static String CONTENT_METADATA = "context_metadata";// 文件元数据

	public final static String CONTENT_FILE = "context_file";// 文件

	public final static String CONTENT_FILE_MD5 = "content_file_md5";// 文件MD5串(2011/10/17添加)

	public final static String DCTM_OBJECT_ID = "dctm_object_id";// DOUCMENTUM
																	// OBJECTID(2011/11/24添加)

	public final static String SOURCE_SYS_CODE = "source_sys_code";// 源系统编码(2011/11/24添加)

	public final static String FILESYNC_THREADNUMBER = "fileSync.ThreadNumber";

	public final static String FILESYNC_UPLOADPROTOCOL = "fileSync.uploadProtocol";

	public final static String HTTP_UPLOAD_URL = "fileSyncServlet";

	public final static String HTTP_PARAMS_FILECONTEXT = "FILE_SYNC_CONTEXT";

	public final static String HTTP_OPERATION_TYPE = "HTTP_OPERATION_TYPE";

	public final static AttributeKey KEY_IN = new AttributeKey(
			StreamIoHandler.class, "in");

	public final static AttributeKey KEY_OUT = new AttributeKey(
			StreamIoHandler.class, "out");

	public final static AttributeKey KEY_FILE = new AttributeKey(
			Constant.class, "file");

	public final static AttributeKey KEY_TASK = new AttributeKey(
			Constant.class, "task");

	public final static String FILTER_OBJ = "object";

	// ===============以下为将来数据库可配置参数=================
	// 每次获取文件记录数量
	public final static String FILE_GET_NUM = "SYNC_FILE_GET_NUM";

	// 文件传输失败后重试时间间隔（单位:秒）
	public final static String FILE_RETRY_INTERVAL = "SYNC_FILE_RETRY_INTERVAL";

	// 文件长期传输中重新进行传输时间间隔 （单位:秒）
	public final static String FIX_RETRY_INTERVAL = "SYNC_FIX_RETRY_INTERVAL";

	// 轮询ORACLE纵向交换任务表时间间隔（单位:秒）
	public final static String FILE_GET_INTERVAL = "SYNC_FILE_GET_INTERVAL";

	// 进行分发/上传的线程池大小
	public final static String THREAD_POOL_SIZE = "SYNC_THREAD_POOL_SIZE";

	// 传输协议类型
	public final static String TRANSMISSION_PROTOCOL = "SYNC_TRANSMISSION_PROTOCOL";

	// socket服务监听的端口
	public final static String SYNC_SERVER_LISSENT_PORT = "SYNC_SERVER_LISSENT_PORT";

	// socket服务ip地址
	public final static String SYNC_SERVER_LISSENT_ADDR = "SYNC_SERVER_LISSENT_ADDR";
	// 同步重试次数
	public final static String SYNC_TRY_COUNT = "SYNC_TRY_COUNT";
	// 纵向交换每M传输超时时间，单位：秒
	public final static String SYNC_TIMEOUT_MPER = "SYNC_TIMEOUT_MPER";
	// Job判断为不活跃的时间单位秒
	public final static String SYNC_JOB_DEATH = "SYNC_JOB_DEATH";
	// 即时通迅returType 异常
	public final static String SYNC_RETURN_TYPE_ERROR = "0";
	// 即时通迅returType 文件在本地
	public final static String SYNC_RETURN_TYPE_LOCAL = "1";
	// 即时通迅returType 返回文件流
	public final static String SYNC_RETURN_TYPE_IO = "2";
	// 即时通迅returType 没有分发任务
	public final static String SYNC_RETURN_TYPE_NO_TASK = "3";
	// 即时通迅returType 正在分发中
	public final static String SYNC_RETURN_TYPE_SEND = "4";
	// 即时通迅returType 正在分发中
	public final static String SYNC_RETURN_TYPE_POOL_OUT = "5";

	// 同时往一个部署点传输的文件最大大小
	public final static String SYNC_FILE_MAX_SIZE_PER_POINT = "SYNC_FILE_MAX_SIZE_PER_POINT";

}
