package com.tutorial.lucene35;

import java.io.IOException;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;

public class MyCustomFilter {

	public void search(String value) {

		try {
			IndexSearcher searcher = new IndexSearcher(IndexReader
					.open(FileDemoUtil.getDirectory()));
			Query query = new TermQuery(new Term("content", value));
			TopDocs tds = searcher.search(query, new MyIdFilter(), 100);
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				Document doc = searcher.doc(sd.doc);
				System.out.println(sd.doc + ":(" + sd.score + ")-->"
						+ doc.get("score") + doc.get("path") + "-->"
						+ doc.get("name") + "-->" + doc.get("size") + "-->"
						+ doc.get("id"));
			}
			searcher.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
