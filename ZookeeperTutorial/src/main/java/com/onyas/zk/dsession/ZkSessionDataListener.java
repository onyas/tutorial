package com.onyas.zk.dsession;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * @author hxpwangyi@163.com
 * @date 2013-3-2
 */
public class ZkSessionDataListener implements IZkDataListener {

	public void handleDataChange(String dataPath, Object data) throws Exception {
		ZkClient client=ZkConnectionSingleton.getInstance();
		String sid=dataPath.substring(dataPath.lastIndexOf("/")+1);
		ZkSession session=(ZkSession)ZkSessionManager.getInstance().getSession(sid);
		SessionMetaData meta=client.readData(dataPath);
		session.setMeta(meta);
		
	}

	public void handleDataDeleted(String dataPath) throws Exception {
		// TODO Auto-generated method stub
		
	}

	

	
}
