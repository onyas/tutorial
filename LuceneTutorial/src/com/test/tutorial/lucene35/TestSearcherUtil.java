package com.test.tutorial.lucene35;

import org.junit.Before;
import org.junit.Test;

import com.tutorial.lucene35.SearcherUtil;

public class TestSearcherUtil {

	private SearcherUtil su;

	@Before
	public void init() {
		su = new SearcherUtil();
	}

	@Test
	public void testSearchByTerm() {
		su.searchByTerm("id", "1", 3);
	}

	@Test
	public void testSearchByTermRange() {
		su.searchByTermRange("id", "1", "3", 10);
		su.searchByTermRange("name", "a", "t", 10);
		//结果为0，表示不支持数字范围的查找
		su.searchByTermRange("attach", "1", "5", 10);
	}
	
	@Test
	public void testNumericRangeQuery() {
		su.searchByNumericRangeQuery("attach", 2,3, 10);
	}
	
	@Test
	public void testSearchByPrefix() {
		su.searchByPrefix("name","j", 10);
		su.searchByPrefix("content","n", 10);
	}
	
	@Test
	public void testSearchByWildCard() {
		//查找名字是以j开头的
		su.searchByWildCard("name","j*", 10);
		//查找是以qq.com结尾的email
		su.searchByWildCard("from","*qq.com", 10);
		//查找名字中有k的
		su.searchByWildCard("name","*k*", 10);
	}
}
