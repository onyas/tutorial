package com.onyas.zk.dsession;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author hxpwangyi@163.com
 * @date 2013-3-1
 */
public class ZkSessionHelper {
	public static final String root="/tianque-session-root-test";
	
	public static void setAttribute(String sid,String name,Object value){
		ZkClient client=ZkConnectionSingleton.getInstance();
		if(!client.exists(root+"/"+sid+"/"+name)){
			client.createPersistent(root+"/"+sid+"/"+name);
		}
		client.writeData(root+"/"+sid+"/"+name, value);
	}
	
	public static void removeAttribute(String sid,String name){
		ZkClient client=ZkConnectionSingleton.getInstance();
		if(client.exists(root+"/"+sid+"/"+name)){
			client.delete(root+"/"+sid+"/"+name);
		}
	}
	
	public static void addSession(SessionMetaData meta){
		ZkClient client=ZkConnectionSingleton.getInstance();
		client.createPersistent(root+"/"+meta.getSid());
		client.writeData(root+"/"+meta.getSid(), meta);
	}
	
}
