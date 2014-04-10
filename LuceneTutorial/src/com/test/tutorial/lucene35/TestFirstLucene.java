package com.test.tutorial.lucene35;

import org.junit.Test;

import com.tutorial.lucene35.FirstLucene;

public class TestFirstLucene {

	@Test
	public void testIndex(){
		FirstLucene lucene = new FirstLucene();
		lucene.index();
	}
	
}
