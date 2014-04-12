package com.tutorial.lucene35;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class SearcherUtil {

	private String[] ids = { "1", "2", "3", "4", "5", "6" };
	private String[] names = { "zhangsan", "lisi", "wangwu", "jake", "johe",
			"Make" };
	private String[] froms = { "aa@sina.com", "bb@163.com", "cc@qq.com",
			"dd@qq.com", "ee@163.com", "ff@qq.com" };
	private String[] contents = { "hi,nice", "hi nice shirt", "hi nice boy",
			"hi nice nice", "nice shot", "nice hhhhh" };
	private int[] attachs = { 3, 2, 3, 4, 2, 1 };
	private Date[] dates = null;
	// ����Ȩֵ����Ϣ
	private Map<String, Float> scores = new HashMap<String, Float>();

	private Directory directory = null;

	private static IndexReader reader;

	public SearcherUtil() {
		directory = new RAMDirectory();
		setDates();
		index();
	}

	/**
	 * �õ�IndexSearcher
	 * 
	 * @return
	 */
	public IndexSearcher getSearcher() {
		try {
			if (reader == null) {
				reader = IndexReader.open(directory);
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

	/**
	 * �������� 1������Directory 2������IndexWriter 3������Document���� 4��ΪDocument���Field
	 * 5��ͨ��IndexWriter����ĵ���������
	 */
	public void index() {
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(directory, new IndexWriterConfig(
					Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
			writer.deleteAll();
			writer.commit();
			Document doc = null;
			for (int i = 0; i < ids.length; i++) {
				doc = new Document();
				doc.add(new Field("id", ids[i], Field.Store.YES,
						Field.Index.NOT_ANALYZED_NO_NORMS));
				doc.add(new Field("name", names[i], Field.Store.YES,
						Field.Index.ANALYZED));
				doc.add(new Field("from", froms[i], Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("content", contents[i], Field.Store.NO,
						Field.Index.ANALYZED));
				doc.add(new NumericField("attach", Field.Store.YES, false)
						.setIntValue(attachs[i]));
				// �洢����
				doc.add(new NumericField("date", Field.Store.YES, false)
						.setLongValue(dates[i].getTime()));
				// ���ݷ�ֵ�д洢�����ͣ�Ϊdoc���ò�ͬ��Ȩֵ
				String from = froms[i].substring(froms[i].lastIndexOf("@") + 1);
				System.out.println(from);
				if (scores.containsKey(from)) {
					doc.setBoost(scores.get(from));
				} else {
					doc.setBoost(1.0f);
				}

				writer.addDocument(doc);
			}

		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (CorruptIndexException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * ��ȷ��ѯ
	 * @param field Ҫ���ҵ���
	 * @param name ����Ҫ���ҵ�����
	 * @param nums ���ҵ�����
	 */
	public void searchByTerm(String field, String name, int nums) {
		try {
			// 3������IndexReader����IndexSearcher
			IndexSearcher searcher = getSearcher();
			// 4������������Query
			TermQuery query = new TermQuery(new Term(field, name));
			// 5������searcher�������ҷ���TopDocs
			TopDocs tds = searcher.search(query, nums);
			System.out.println("һ����ѯ��:" + tds.totalHits);
			// 6������TopDocs��ȡScoreDoc����
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 7������searcher��ScoreDoc�����ȡ�����Document����
				Document doc = searcher.doc(sd.doc);
				// 8������Document�����ȡ��Ҫ��ֵ
				System.out.println("score:"+sd.score + "--" + sd.doc + "--id:" + doc.get("id") + "--from:"
						+ doc.get("from") + "--name:" + doc.get("name") + "--content:"
						+ doc.get("content") + "--attach:" + doc.get("attach") + "--date:"
						+ doc.get("date"));
			}
			searcher.close();
		} catch (CorruptIndexException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	private void setDates() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		dates = new Date[ids.length];
		try {
			dates[0] = sdf.parse("2014-4-11");
			dates[1] = sdf.parse("2014-5-21");
			dates[2] = sdf.parse("2014-2-25");
			dates[3] = sdf.parse("2014-1-19");
			dates[4] = sdf.parse("2014-7-25");
			dates[5] = sdf.parse("2014-9-16");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
