package com.test.tutorial.lucene35;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.NumericRangeFilter;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermRangeFilter;
import org.apache.lucene.search.WildcardQuery;
import org.junit.Before;
import org.junit.Test;

import com.tutorial.lucene35.SearcherSort;

public class TestSearcherSort {

	private SearcherSort ss = null;

	@Before
	public void init() {
		ss = new SearcherSort();
	}

	@Test
	public void testFileSearcher() {

		 ss.searcher("String", null);//默认情况下用系统的评分

		// ss.searcher("String", Sort.RELEVANCE);//与默认情况下一样

		// ss.searcher("String",Sort.INDEXORDER);//按序号进行排序，sd.doc

		// 按照别的字段进行排序，并逆序(从高到低)进行排序
		// ss.searcher("String", new Sort(new SortField("size",
		// SortField.LONG,true)));
		// ss.searcher("String", new Sort(new SortField("time",
		// SortField.LONG,true)));

		// 先按文件大小排序，如果大小一样，则按序号进行排序
		ss.searcher("String", new Sort(new SortField("size", SortField.LONG),
				SortField.FIELD_DOC));

	}

	@Test
	public void testSearcherWithFilter() {

		Filter filter = new TermRangeFilter("name", "java.aaa", "java.txt", true, true);
		filter = NumericRangeFilter.newLongRange("time", 1397572186840L, 1997572186840L,
				true, true);
		filter = new QueryWrapperFilter(new WildcardQuery(new Term("name","*.txt")));
		ss.searcherWithFilter("String", filter);
	}

}
