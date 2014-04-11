package com.test.tutorial.lucene35;

import org.junit.Test;

import com.tutorial.lucene35.IndexUtil;

public class TestIndexUtil {

	@Test
	public void testIndex(){
		IndexUtil iu = new IndexUtil();
		iu.index();
	}
	
	@Test
	public void testQuery(){
		IndexUtil iu = new IndexUtil();
		iu.query();
	}
	
	@Test
	public void testDelete(){
		IndexUtil iu = new IndexUtil();
		iu.delete();
	}
	
	@Test
	public void testRecover(){
		IndexUtil iu = new IndexUtil();
		iu.recover();
	}
	
	
	@Test
	public void testForceMergeDeletes(){
		IndexUtil iu = new IndexUtil();
		iu.forceMergeDeletes();
	}
	
	
	@Test
	public void testUpdate(){
		IndexUtil iu = new IndexUtil();
		iu.update();
	}
	
	

	@Test
	public void testSearch(){
		IndexUtil iu = new IndexUtil();
		for(int i=0;i<5;i++){
			iu.search();
			System.out.println("--------");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
