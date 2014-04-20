package com.tutorial.tika;

import org.junit.Test;

public class TestTika {

	@Test
	public void TestIndex(){
		FileDemoUtil.indexFiles(true);
	}
	
	
	@Test
	public void TestSearcher(){
		SearchUtil su = new SearchUtil();
		su.searcher();
	}
}
