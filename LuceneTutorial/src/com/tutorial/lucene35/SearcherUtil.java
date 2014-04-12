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
	// 定义权值的信息
	private Map<String, Float> scores = new HashMap<String, Float>();

	private Directory directory = null;

	private static IndexReader reader;

	public SearcherUtil() {
		directory = new RAMDirectory();
		setDates();
		index();
	}

	/**
	 * 得到IndexSearcher
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
	 * 精确查询
	 * @param field 要查找的域
	 * @param name 域中要查找的名字
	 * @param nums 查找的数量
	 */
	public void searchByTerm(String field, String name, int nums) {
		try {
			// 3、根据IndexReader创建IndexSearcher
			IndexSearcher searcher = getSearcher();
			// 4、创建搜索的Query
			TermQuery query = new TermQuery(new Term(field, name));
			// 5、根据searcher搜索并且返回TopDocs
			TopDocs tds = searcher.search(query, nums);
			System.out.println("一共查询到:" + tds.totalHits);
			// 6、根据TopDocs获取ScoreDoc对象
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 7、根据searcher和ScoreDoc对象获取具体的Document对象
				Document doc = searcher.doc(sd.doc);
				// 8、根据Document对象获取需要的值
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
