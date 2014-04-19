package com.test.tutorial.lucene35;

import org.junit.Test;

import com.tutorial.lucene35.MyCustomFilter;

public class TestMyFilter {

	@Test
	public void testSearch(){
		MyCustomFilter mcf = new MyCustomFilter();
		mcf.search("java");
	}
	
}
