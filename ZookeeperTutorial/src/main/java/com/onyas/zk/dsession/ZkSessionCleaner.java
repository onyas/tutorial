package com.onyas.zk.dsession;

import java.util.Date;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author hxpwangyi@163.com
 * @date 2013-3-2
 */
public class ZkSessionCleaner extends Thread {

	@Override
	public void run() {
		ZkClient client=ZkConnectionSingleton.getInstance();
		while(true){
			List<String> sessionList=client.getChildren(ZkSessionHelper.root);
			for(int i=0,len=sessionList.size();i<len;i++){
				String sid=sessionList.get(i);
				SessionMetaData meta=client.readData(ZkSessionHelper.root+"/"+sid);
				ZkSession session=new ZkSession();
				if((new Date().getTime()- meta.getLastAccessedTime())>meta.getMaxInactiveInterval()){
	
					client.deleteRecursive(ZkSessionHelper.root+"/"+sid);
				}
			}
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
			}
		}
	}

}
