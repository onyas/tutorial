package com.test.tutorial.lucene35;

import org.junit.Test;

import com.tutorial.lucene35.CustomQueryParseUtil;

public class TestCustomQueryParser {

	@Test
	public void testQuery(){
		CustomQueryParseUtil cp = new CustomQueryParseUtil();
//		cp.search("java?");
//		cp.search("java~");
//		cp.search("size:[1000 TO 2000]");
//		cp.search("name:[a TO h]");
//		cp.search("time:[333 TO 3333]");
//		cp.search("time:[2014-04-09 TO 2014-04-11]");
		cp.search("time:[2014-04-07 TO 2014-04-09]");
	}
	
}
