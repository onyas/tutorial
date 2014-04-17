package com.test.tutorial.lucene35;

import org.junit.Test;

import com.tutorial.lucene35.CustomScore;

public class TestCustomScore {

	@Test
	public void testsearchByCustomScore(){
		CustomScore cs = new CustomScore();
		cs.searchByCustomScore();
	}
	
	
	@Test
	public void searchByFilenameScore(){
		CustomScore cs = new CustomScore();
		cs.searchByFilenameScore();
	}
}
