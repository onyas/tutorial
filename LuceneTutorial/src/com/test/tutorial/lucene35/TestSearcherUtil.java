package com.test.tutorial.lucene35;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
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
		// ���Ϊ0����ʾ��֧�����ַ�Χ�Ĳ���
		su.searchByTermRange("attach", "1", "5", 10);
	}

	@Test
	public void testNumericRangeQuery() {
		su.searchByNumericRangeQuery("attach", 2, 3, 10);
	}

	@Test
	public void testSearchByPrefix() {
		su.searchByPrefix("name", "j", 10);
		su.searchByPrefix("content", "n", 10);
	}

	@Test
	public void testSearchByWildCard() {
		// ������������j��ͷ��
		su.searchByWildCard("name", "j*", 10);
		// ��������qq.com��β��email
		su.searchByWildCard("from", "*qq.com", 10);
		// ������������k��
		su.searchByWildCard("name", "*k*", 10);
	}

	@Test
	public void testSearchByBoolean() {
		su.searchByBooleanQuery(10);
	}

	@Test
	public void testSearchByPhrase() {
		su.searchByPhrase(10);
	}

	@Test
	public void testSearchByFuzzy() {
		su.searchByFuzzy("name", "lake", 10);
	}

	@Test
	public void testSearchByQueryParse() throws Exception {
		//����QueryParser����Ĭ��������Ϊcontent
		QueryParser parser = new QueryParser(Version.LUCENE_35, "content",
				new StandardAnalyzer(Version.LUCENE_35));
		//����content�а�����shot��
		Query query = parser.parse("shot");
		
		//����content�а�����hi��boy�ģ��ո����˼�ǻ�
		query = parser.parse("hi boy");
//		parser.setDefaultOperator(Operator.AND);//���Ըı�ո����˼
		
		//����content�а�����nice boy�������ģ���Ϊ�пո�����Ҫ��""����ת��
		query = parser.parse("\"nice boy\"");
		
		//����content�а�����nice����shirt�ģ��м��AND(�����д)��ʾ�ҵĹ�ϵ
		query = parser.parse("nice AND shirt");
		
		//�ı�������Ϊname,����������Ϊjake
		query = parser.parse("name:jake");
		
		//��*��?������ͨ���ƥ��,Ĭ������£����ܷ��ڵ�һλ����Ϊ�ή��Ч�ʣ������Խ�������
		query = parser.parse("name:j*");
		parser.setAllowLeadingWildcard(true);
		query = parser.parse("from:*qq.com");
		
		//ƥ��������û��jake,��content��Ҫ��hhhhh
		query = parser.parse("- name:jake + content:hhhhh");
		
		//������ƥ��
		query = parser.parse("id:[1 TO 3]");
		
		//������ƥ��
		query = parser.parse("id:{1 TO 3}");
		
		
		su.searchByQueryParse(query, 10);
	}
	
	@Test
	public void testpageSearcher1(){
		su.pageSearcher1("String",3, 5);
		System.out.println("--------");
		su.pageSearcher1("String",3, 5);
	}
}
