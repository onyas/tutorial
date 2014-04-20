package com.tutorial.tika;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;

public class SearchUtil {

	public void searcher(){
		try {
			IndexSearcher searcher = new IndexSearcher(IndexReader.open(FileDemoUtil
					.getDirectory()));
			TermQuery query = new TermQuery(new Term("content","string"));
			TopDocs tds = searcher.search(query, 20);
			for(ScoreDoc sd:tds.scoreDocs){
				Document doc = searcher.doc(sd.doc);
				System.out.println(doc.get("title"));
			}
			searcher.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
