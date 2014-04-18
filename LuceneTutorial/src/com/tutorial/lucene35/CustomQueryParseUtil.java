package com.tutorial.lucene35;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;

public class CustomQueryParseUtil {

	public void search(String value) {

		try {
			IndexSearcher searcher = new IndexSearcher(IndexReader
					.open(FileDemoUtil.getDirectory()));

			CustomQueryParser parser = new CustomQueryParser(Version.LUCENE_35,
					"content", new StandardAnalyzer(Version.LUCENE_35));

			Query query = parser.parse(value);
			
			TopDocs tds = searcher.search(query, 100);
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
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
	}

}
