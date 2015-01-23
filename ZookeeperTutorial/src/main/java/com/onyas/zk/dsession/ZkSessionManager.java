package com.onyas.zk.dsession;

import java.util.List;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author hxpwangyi@163.com
 * @date 2013-3-2
 */
public class ZkSessionManager extends AbstractSessionManager {
	private static AbstractSessionManager instance=new ZkSessionManager();
	
	@Override
	public void loadSession() {
		ZkClient client=ZkConnectionSingleton.getInstance();
		List<String> sessionList=client.getChildren(ZkSessionHelper.root);
		for(int i=0,len=sessionList.size();i<len;i++){
			String sid=sessionList.get(i);
			SessionMetaData meta=client.readData(ZkSessionHelper.root+"/"+sid);
			ZkSession session=new ZkSession();
			session.setId(sid);
			session.setMeta(meta);
			List<String> attributeList=client.getChildren(ZkSessionHelper.root+"/"+sid);
			for(int j=0,size=attributeList.size();j<size;j++){
				String name=attributeList.get(j);
				Object value=client.readData(ZkSessionHelper.root+"/"+sid+"/"+name);
				session.localSetAttribute(name, value);
			}
			AbstractSessionManager sessionManager=ZkSessionManager.getInstance();
			sessionManager.addSession(session, sid);
		}

	}
	
	public static AbstractSessionManager getInstance(){
		return instance;
	}

}
