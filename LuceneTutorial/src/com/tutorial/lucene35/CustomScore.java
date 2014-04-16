package com.tutorial.lucene35;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.function.CustomScoreProvider;
import org.apache.lucene.search.function.CustomScoreQuery;
import org.apache.lucene.search.function.FieldScoreQuery;
import org.apache.lucene.search.function.ValueSourceQuery;
import org.apache.lucene.search.function.FieldScoreQuery.Type;

public class CustomScore {

	/**
	 * 根据自定义评分进行搜索
	 */
	public void searchByCustomScore() {

		try {
			IndexSearcher searcher = new IndexSearcher(IndexReader
					.open(FileDemoUtil.getDirectory()));
			
			Query q = new TermQuery(new Term("content", "string"));
			//1、创建一个评分域
			FieldScoreQuery fd = new FieldScoreQuery("score",Type.INT);
			//2、根据评分域和原有的Query创建自定义的Query对象；
			MyCustomScoreQuery query = new MyCustomScoreQuery(q, fd);
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
		}
	}

	//
	private class MyCustomScoreQuery extends CustomScoreQuery{

		public MyCustomScoreQuery(Query subQuery, ValueSourceQuery valSrcQuery) {
			super(subQuery, valSrcQuery);
		}
		
		@Override
		protected CustomScoreProvider getCustomScoreProvider(IndexReader reader)
				throws IOException {
			//默认情况下是通过原有的评分*传入进来的评分域所获取的评分来确定最终的打分
			//为了根据不同的需求进行评分，需要自己进行评分的设定
			//步骤1、创建一个类继承于CustomScoreProvider
			//2、覆盖customScore方法
//			return super.getCustomScoreProvider(reader);
			return new MyCustomScoreProvider(reader);
		}
	}
	
	private class MyCustomScoreProvider extends CustomScoreProvider{

		public MyCustomScoreProvider(IndexReader reader) {
			super(reader);
		}
		
		/**
		 * subQueryScore 默认文档的打分
		 * valSrcScore 评分域的打分
		 */
		@Override
		public float customScore(int doc, float subQueryScore, float valSrcScore)
				throws IOException {
			return subQueryScore/valSrcScore;
		}
		
	}
	
}
