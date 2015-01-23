package com.onyas.zk.dsession;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * @author hxpwangyi@163.com
 * @date 2013-3-1
 */
public class ZkSessionChangeListener extends SessionChangeListener {
	private static final Map<String, Set<IZkDataListener>> sissionDataListeners=new HashMap<String, Set<IZkDataListener>>();
	private static final Map<String,Map<String, Set<IZkDataListener>>> attributeDataListeners= new HashMap<String,Map<String, Set<IZkDataListener>>>();
	
	private void subscribeSessionAttributeChange(List<String> zkSessions){
		final ZkClient zkClient=ZkConnectionSingleton.getInstance();
		for(int i=0,len=zkSessions.size();i<len;i++){
			String sid=(String)zkSessions.get(i);
			List<String> zkSessionAttributes=zkClient.getChildren(ZkSessionHelper.root+"/"+sid);
			Map<String, Set<IZkDataListener>> attributesDataListener=attributeDataListeners.get(sid);
			
			for(int j=0,size=zkSessionAttributes.size();j<size;j++){
				String name=zkSessionAttributes.get(j);
				IZkDataListener newDataListener=new ZkSessionAttributeDataListener();
				if(attributesDataListener==null){
					attributesDataListener=new HashMap<String, Set<IZkDataListener>>();
					Set<IZkDataListener> attributeDataListener=new HashSet<IZkDataListener>();
					attributeDataListener.add(newDataListener);
					attributesDataListener.put(name, attributeDataListener);
					attributeDataListeners.put(sid, attributesDataListener);
				}else{
					Set<IZkDataListener> attributeDataListener=attributesDataListener.get(name);
					if(attributeDataListener!=null){
						 Iterator<IZkDataListener> it=attributeDataListener.iterator();
						 while(it.hasNext()){
							 zkClient.unsubscribeDataChanges(ZkSessionHelper.root+"/"+sid+"/"+name, it.next());
						 }
					}else{
						attributeDataListener=new HashSet<IZkDataListener>();
						attributeDataListener.add(newDataListener);
					}
				}
				zkClient.subscribeDataChanges(ZkSessionHelper.root+"/"+sid+"/"+name,newDataListener );
			}
			
		}
	}
	
	private void subscribeSessionDataChange(List<String> zkSessions){
		final ZkClient zkClient=ZkConnectionSingleton.getInstance();
		for(int i=0,len=zkSessions.size();i<len;i++){
			String sid=(String)zkSessions.get(i);
			Set<IZkDataListener> listenerSet=sissionDataListeners.get(sid);
			if(listenerSet!=null){
				Iterator<IZkDataListener> it=listenerSet.iterator();
				while(it.hasNext()){
					zkClient.unsubscribeDataChanges(ZkSessionHelper.root+"/"+sid, it.next());
				}
			}
			IZkDataListener newDataListener=new ZkSessionDataListener();
			if(listenerSet==null){
				listenerSet=new HashSet<IZkDataListener>();
				listenerSet.add(newDataListener);
				sissionDataListeners.put(sid, listenerSet);
			}else{
				listenerSet.add(newDataListener);
			}
			zkClient.subscribeDataChanges(ZkSessionHelper.root+"/"+sid,newDataListener );
		}
		subscribeSessionAttributeChange(zkSessions);
	}
 	protected void subscribeSession() {
		final ZkClient zkClient=ZkConnectionSingleton.getInstance();
		if(!zkClient.exists(ZkSessionHelper.root)){
			zkClient.createPersistent(ZkSessionHelper.root);
		}
		ZkSessionManager.getInstance().loadSession();
		new ZkSessionCleaner().start();
		List<String> zkSessions=zkClient.getChildren(ZkSessionHelper.root);
		subscribeSessionDataChange(zkSessions);
		zkClient.subscribeChildChanges(ZkSessionHelper.root, new IZkChildListener() {

			public void handleChildChange(String parentPath,
					List<String> currentChilds) throws Exception {

				  subscribeSessionDataChange(currentChilds);
				  //subscribeSessionAttributeChange(currentChilds);
				   Map sessions=ZkSessionManager.getInstance().getAllSession();
				   for(Object sid: sessions.keySet()){
					   boolean has=false;
					   for(int j=0, len=currentChilds.size();j<len;j++){
						   if(((String)sid).equals(currentChilds.get(j))){
							   has=true;
							   break;
						   }
					   }
					   if(!has){
						   sessions.remove(sid);
					   }
				   }
				   for(int j=0, len=currentChilds.size();j<len;j++){
					   boolean has=false;
					   String zkSid=(String)currentChilds.get(j);
					   for(Object sid: sessions.keySet()){
						   if(((String)sid).equals(zkSid)){
							   has=true;
							   break;
						   }
					   }
					   if(!has){
						   SessionMetaData meta=zkClient.readData(ZkSessionHelper.root+"/"+zkSid);
						   ZkSession session=new ZkSession();
						   session.setMeta(meta);
						   ZkSessionManager.getInstance().addSession(session, session.getId());
						   List<String> keys=zkClient.getChildren(ZkSessionHelper.root+"/"+zkSid);
						   for(int i=0,size=keys.size();i<size;i++){
							   Object val=zkClient.readData(keys.get(i));
							   session.localSetAttribute(keys.get(i), val);
						   }
					   }
				   }
			
				
			}
			
		});
	}
	
	

	protected void release() {
		

	}

}
