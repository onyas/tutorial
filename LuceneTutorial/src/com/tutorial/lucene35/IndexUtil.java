package com.tutorial.lucene35;

import java.io.File;
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
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

public class IndexUtil {

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

	public IndexUtil() {
		try {
			directory = FSDirectory.open(new File("F:/Test/lucene/index02"));
			scores.put("qq.com", 3.0f);
			scores.put("sina.com", 2.0f);
			setDates();
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
	 * ��ѯ
	 */
	public void query() {
		try {
			IndexReader reader = IndexReader.open(directory);
			// ͨ��reader������Ч�Ļ�ȡ���ĵ�������
			System.out.println("numDocs:" + reader.numDocs());
			System.out.println("maxDoc:" + reader.maxDoc());
			System.out.println("deletedDocs:" + reader.numDeletedDocs());
			reader.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ɾ������
	 */
	public void delete() {
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(directory, new IndexWriterConfig(
					Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
			// ������һ��ѡ�������һ��Query,Ҳ������һ��Term��term��һ����ȷ���ҵ�ֵ
			writer.deleteDocuments(new Term("id", "1"));
			// ��ʱɾ�����ĵ�������ȫɾ�������Ǵ洢��һ������վ�У����Իָ�
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (CorruptIndexException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * �ָ�ɾ��������
	 */
	public void recover() {
		try {
			// �ָ�ʱ�������IndexReader��ֻ��(readOnly)����Ϊfalse
			IndexReader reader = IndexReader.open(directory, false);
			reader.undeleteAll();
			reader.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ǿ��ɾ������
	 */
	public void forceMergeDeletes() {
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(directory, new IndexWriterConfig(
					Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
			writer.forceMergeDeletes();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (CorruptIndexException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * ��������
	 */
	public void update() {
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(directory, new IndexWriterConfig(
					Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));

			// Lucene����֧�ָ��£�����ĸ��²�����ʵ����ɾ�������
			Document doc = new Document();
			doc.add(new Field("id", "11", Field.Store.YES,
					Field.Index.NOT_ANALYZED_NO_NORMS));
			doc.add(new Field("name", "update", Field.Store.YES,
					Field.Index.ANALYZED));
			doc.add(new Field("from", "update", Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			doc.add(new Field("content", "update", Field.Store.NO,
					Field.Index.ANALYZED));
			writer.updateDocument(new Term("id", "1"), doc);
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (CorruptIndexException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * ����
	 */
	public void search() {
		try {
			// 2������IndexReader
			IndexReader reader = IndexReader.open(directory);
			// 3������IndexReader����IndexSearcher
			IndexSearcher searcher = new IndexSearcher(reader);
			// 4������������Query
			TermQuery query = new TermQuery(new Term("content", "nice"));
			// 5������searcher�������ҷ���TopDocs
			TopDocs tds = searcher.search(query, 10);
			// 6������TopDocs��ȡScoreDoc����
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 7������searcher��ScoreDoc�����ȡ�����Document����
				Document doc = searcher.doc(sd.doc);
				// 8������Document�����ȡ��Ҫ��ֵ
				System.out.println(sd.doc + "--" + doc.get("from") + "---"
						+ doc.get("name") + "--" + doc.get("content") + "--"
						+ doc.get("attach") + "--" + doc.get("date"));
			}
			// 9���ر�reader
			reader.clone();
		} catch (CorruptIndexException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
