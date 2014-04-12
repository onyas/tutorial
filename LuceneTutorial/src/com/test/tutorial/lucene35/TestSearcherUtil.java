package com.test.tutorial.lucene35;

import org.junit.Before;
import org.junit.Test;

import com.tutorial.lucene35.SearcherUtil;

public class TestSearcherUtil {

	private SearcherUtil su;
	
	@Before
	public void init(){
		su = new SearcherUtil();
	}
	

	@Test
	public void testSearchByTerm(){
		su.searchByTerm("id","1", 3);
	}

}

