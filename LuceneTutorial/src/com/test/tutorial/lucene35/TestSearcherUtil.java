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
		// 结果为0，表示不支持数字范围的查找
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
		// 查找名字是以j开头的
		su.searchByWildCard("name", "j*", 10);
		// 查找是以qq.com结尾的email
		su.searchByWildCard("from", "*qq.com", 10);
		// 查找名字中有k的
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
		//创建QueryParser对象，默认搜索域为content
		QueryParser parser = new QueryParser(Version.LUCENE_35, "content",
				new StandardAnalyzer(Version.LUCENE_35));
		//搜索content中包含有shot的
		Query query = parser.parse("shot");
		
		//搜索content中包含有hi或boy的，空格的意思是或
		query = parser.parse("hi boy");
//		parser.setDefaultOperator(Operator.AND);//可以改变空格的意思
		
		//搜索content中包含有nice boy这个短语的，因为有空格，所以要加""进行转义
		query = parser.parse("\"nice boy\"");
		
		//搜索content中包含有nice且有shirt的，中间加AND(必须大写)表示且的关系
		query = parser.parse("nice AND shirt");
		
		//改变搜索域为name,且搜索名字为jake
		query = parser.parse("name:jake");
		
		//用*或?来进行通配符匹配,默认情况下，不能放在第一位，因为会降低效率，但可以进行设置
		query = parser.parse("name:j*");
		parser.setAllowLeadingWildcard(true);
		query = parser.parse("from:*qq.com");
		
		//匹配名字中没有jake,但content中要有hhhhh
		query = parser.parse("- name:jake + content:hhhhh");
		
		//闭区间匹配
		query = parser.parse("id:[1 TO 3]");
		
		//开区间匹配
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
