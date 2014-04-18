package com.test.tutorial.lucene35;

import org.junit.Test;

import com.tutorial.lucene35.CustomQueryParseUtil;

public class TestCustomQueryParser {

	@Test
	public void testQuery(){
		CustomQueryParseUtil cp = new CustomQueryParseUtil();
		cp.search("java?");
		cp.search("java~");
	}
	
}
