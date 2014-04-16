package com.tutorial.lucene35;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;

public class SearcherSort {

	private static IndexReader reader = null;

	static {
		try {
			reader = IndexReader.open(FileDemoUtil.getDirectory());
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public IndexSearcher getSearcher() {
		try {
			if (reader == null) {
				reader = IndexReader.open(FileDemoUtil.getDirectory());
			} else {
				IndexReader ir = IndexReader.openIfChanged(reader);
				if (ir != null) {
					reader.close();
					reader = ir;
				}
			}
			return new IndexSearcher(reader);
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void searcher(String queryStr, Sort sort) {

		try {
			IndexSearcher searcher = getSearcher();
			QueryParser parser = new QueryParser(Version.LUCENE_35, "content",
					new StandardAnalyzer(Version.LUCENE_35));
			Query query = parser.parse(queryStr);
			TopDocs tds = null;
			if (sort != null) {
				tds = searcher.search(query, 50, sort);
			} else {
				tds = searcher.search(query, 50);
			}
			ScoreDoc[] sds = tds.scoreDocs;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (ScoreDoc sd : sds) {
				Document doc = searcher.doc(sd.doc);
				System.out.println(sd.doc + ":(" + sd.score + ")-->"
						+ doc.get("score") + doc.get("path") + "-->"
						+ doc.get("name") + "-->" + doc.get("size") + "-->"
						+ sdf.format(new Date(Long.valueOf(doc.get("time")))));
			}
			searcher.close();
		} catch (CorruptIndexException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据过滤器进行搜索
	 * 
	 * @param queryStr
	 * @param filter
	 */
	public void searcherWithFilter(String queryStr, Filter filter) {

		try {
			IndexSearcher searcher = getSearcher();
			QueryParser parser = new QueryParser(Version.LUCENE_35, "content",
					new StandardAnalyzer(Version.LUCENE_35));
			Query query = parser.parse(queryStr);
			TopDocs tds = null;
			if (filter != null) {
				tds = searcher.search(query, filter, 50);
			} else {
				tds = searcher.search(query, 50);
			}
			ScoreDoc[] sds = tds.scoreDocs;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (ScoreDoc sd : sds) {
				Document doc = searcher.doc(sd.doc);
				System.out.println(sd.doc + ":(" + sd.score + ")"
						+ doc.get("path") + "-->" + doc.get("name") + "-->"
						+ doc.get("size") + "-->"
						+ sdf.format(new Date(Long.valueOf(doc.get("time")))));
			}
			searcher.close();
		} catch (CorruptIndexException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
