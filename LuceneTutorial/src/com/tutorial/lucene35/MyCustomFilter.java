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

	public void search() {

		try {
			IndexSearcher searcher = new IndexSearcher(IndexReader
					.open(FileDemoUtil.getDirectory()));
			Query query = new TermQuery(new Term("content", "java"));
			TopDocs tds = searcher.search(query, new MyFilter(new FilterAccessor() {
				
				@Override
				public boolean set() {
					return false;
				}
				
				@Override
				public String[] getValues() {
					return new String[]{"54"};
				}
				
				@Override
				public String getField() {
					return "id";
				}
			}), 100);
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
