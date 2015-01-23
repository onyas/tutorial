package com.onyas.zk.dsession;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author hxpwangyi@163.com
 * @date 2013-3-1
 */
public class ZkConnectionSingleton {
	private static String zkServer = "127.0.0.1:2181";
	private static ZkClient zkClient=new ZkClient(zkServer);
	
	public static ZkClient getInstance(){
		return zkClient;
	}
}
