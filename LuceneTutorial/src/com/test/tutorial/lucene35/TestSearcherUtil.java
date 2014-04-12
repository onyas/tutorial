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
		//���Ϊ0����ʾ��֧�����ַ�Χ�Ĳ���
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
		//������������j��ͷ��
		su.searchByWildCard("name","j*", 10);
		//��������qq.com��β��email
		su.searchByWildCard("from","*qq.com", 10);
		//������������k��
		su.searchByWildCard("name","*k*", 10);
	}
}
