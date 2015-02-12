package com.onyas.zk.tutorial;

import org.I0Itec.zkclient.ZkClient;
import org.junit.Before;
import org.junit.Test;

public class ZkClientTest {

	private ZkClient zkClient;
	
	@Before
	public void init(){
		zkClient=new ZkClient("127.0.0.1:2181", 5000);
	}
	
	
	@Test
	public void testCreateParents(){
		zkClient.createPersistent("/test/test001/testoo1", true);
	}
	
	@Test
	public void testDelete(){
		zkClient.delete("/test");
	}
	
	@Test
	public void testDeleteRecursive(){
		zkClient.deleteRecursive("/test");
	}
	
}
