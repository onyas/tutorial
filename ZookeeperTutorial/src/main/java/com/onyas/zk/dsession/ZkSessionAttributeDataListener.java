package com.onyas.zk.dsession;

import org.I0Itec.zkclient.IZkDataListener;

/**
 * @author hxpwangyi@163.com
 * @date 2013-3-2
 */
public class ZkSessionAttributeDataListener implements IZkDataListener {

	public void handleDataChange(String dataPath, Object data) throws Exception {
		String name=dataPath.substring(dataPath.lastIndexOf("/")+1);
		Object value=data;
		String prefix=dataPath.substring(0,dataPath.lastIndexOf("/"));
		String sid=prefix.substring(prefix.lastIndexOf("/")+1);
		((ZkSession)ZkSessionManager.getInstance().getSession(sid)).localSetAttribute(name, value);
		
	}

	public void handleDataDeleted(String dataPath) throws Exception {
		
	}

	

}
