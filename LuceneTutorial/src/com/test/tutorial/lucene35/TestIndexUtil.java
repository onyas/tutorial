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
	
}
