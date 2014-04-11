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
	// 定义权值的信息
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
	 * 建立索引 1、创建Directory 2、创建IndexWriter 3、创建Document对象 4、为Document添加Field
	 * 5、通过IndexWriter添加文档到索引中
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
				// 存储日期
				doc.add(new NumericField("date", Field.Store.YES, false)
						.setLongValue(dates[i].getTime()));
				// 根据分值中存储的类型，为doc设置不同的权值
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
	 * 查询
	 */
	public void query() {
		try {
			IndexReader reader = IndexReader.open(directory);
			// 通过reader可以有效的获取到文档的数量
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
	 * 删除索引
	 */
	public void delete() {
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(directory, new IndexWriterConfig(
					Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
			// 参数是一个选项，可以是一个Query,也可以是一个Term，term是一个精确查找的值
			writer.deleteDocuments(new Term("id", "1"));
			// 此时删除的文档不会完全删除，而是存储在一个回收站中，可以恢复
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
	 * 恢复删除的索引
	 */
	public void recover() {
		try {
			// 恢复时，必须把IndexReader的只读(readOnly)设置为false
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
	 * 强制删除索引
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
	 * 更新索引
	 */
	public void update() {
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(directory, new IndexWriterConfig(
					Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));

			// Lucene并不支持更新，这里的更新操作其实是先删除后添加
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
	 * 搜索
	 */
	public void search() {
		try {
			// 2、创建IndexReader
			IndexReader reader = IndexReader.open(directory);
			// 3、根据IndexReader创建IndexSearcher
			IndexSearcher searcher = new IndexSearcher(reader);
			// 4、创建搜索的Query
			TermQuery query = new TermQuery(new Term("content", "nice"));
			// 5、根据searcher搜索并且返回TopDocs
			TopDocs tds = searcher.search(query, 10);
			// 6、根据TopDocs获取ScoreDoc对象
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 7、根据searcher和ScoreDoc对象获取具体的Document对象
				Document doc = searcher.doc(sd.doc);
				// 8、根据Document对象获取需要的值
				System.out.println(sd.doc + "--" + doc.get("from") + "---"
						+ doc.get("name") + "--" + doc.get("content") + "--"
						+ doc.get("attach") + "--" + doc.get("date"));
			}
			// 9、关闭reader
			reader.clone();
		} catch (CorruptIndexException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
