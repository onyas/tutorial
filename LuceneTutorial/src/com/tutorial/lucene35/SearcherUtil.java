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
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.BooleanClause.Occur;
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
	 * �õ�IndexSearcher
	 * 
	 * @return
	 */
	public IndexSearcher getSearcher(Directory directory) {
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
				doc.add(new NumericField("attach", Field.Store.YES, true)
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
	 * 
	 * @param field
	 *            Ҫ���ҵ���
	 * @param name
	 *            ����Ҫ���ҵ�����
	 * @param nums
	 *            ���ҵ�����
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
				System.out.println("score:" + sd.score + "--" + sd.doc
						+ "--id:" + doc.get("id") + "--from:" + doc.get("from")
						+ "--name:" + doc.get("name") + "--content:"
						+ doc.get("content") + "--attach:" + doc.get("attach")
						+ "--date:" + doc.get("date"));
			}
			searcher.close();
		} catch (CorruptIndexException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��Χ����
	 * 
	 * @param field
	 *            Ҫ���ҵ���
	 * @param lower
	 *            ��ʼλ��
	 * @param upper
	 *            ����λ��
	 * @param nums
	 *            �������
	 */
	public void searchByTermRange(String field, String lower, String upper,
			int nums) {
		try {
			// 3������IndexReader����IndexSearcher
			IndexSearcher searcher = getSearcher();
			// 4������������Query
			Query query = new TermRangeQuery(field, lower, upper, true, true);
			// 5������searcher�������ҷ���TopDocs
			TopDocs tds = searcher.search(query, nums);
			System.out.println("һ����ѯ��:" + tds.totalHits);
			// 6������TopDocs��ȡScoreDoc����
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 7������searcher��ScoreDoc�����ȡ�����Document����
				Document doc = searcher.doc(sd.doc);
				// 8������Document�����ȡ��Ҫ��ֵ
				System.out.println("score:" + sd.score + "--" + sd.doc
						+ "--id:" + doc.get("id") + "--from:" + doc.get("from")
						+ "--name:" + doc.get("name") + "--content:"
						+ doc.get("content") + "--attach:" + doc.get("attach")
						+ "--date:" + doc.get("date"));
			}
			searcher.close();
		} catch (CorruptIndexException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���ַ�Χ������
	 * 
	 * @param field
	 *            Ҫ���ҵ���
	 * @param min
	 *            ��Сֵ
	 * @param max
	 *            ���ֵ
	 * @param nums
	 *            ���ҵ�����
	 */
	public void searchByNumericRangeQuery(String field, int min, int max,
			int nums) {
		try {
			// 3������IndexReader����IndexSearcher
			IndexSearcher searcher = getSearcher();
			// 4������������Query
			Query query = NumericRangeQuery.newIntRange(field, min, max, true,
					true);

			// 5������searcher�������ҷ���TopDocs
			TopDocs tds = searcher.search(query, nums);
			System.out.println("һ����ѯ��:" + tds.totalHits);
			// 6������TopDocs��ȡScoreDoc����
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 7������searcher��ScoreDoc�����ȡ�����Document����
				Document doc = searcher.doc(sd.doc);
				// 8������Document�����ȡ��Ҫ��ֵ
				System.out.println("score:" + sd.score + "--" + sd.doc
						+ "--id:" + doc.get("id") + "--from:" + doc.get("from")
						+ "--name:" + doc.get("name") + "--content:"
						+ doc.get("content") + "--attach:" + doc.get("attach")
						+ "--date:" + doc.get("date"));
			}
			searcher.close();
		} catch (CorruptIndexException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ǰ׺����
	 * 
	 * @param field
	 *            Ҫ��������
	 * @param value
	 *            ƥ�������
	 * @param nums
	 *            Ҫ���ҵ��������
	 */
	public void searchByPrefix(String field, String value, int nums) {
		try {
			// 3������IndexReader����IndexSearcher
			IndexSearcher searcher = getSearcher();
			// 4������������Query
			Query query = new PrefixQuery(new Term(field, value));
			// 5������searcher�������ҷ���TopDocs
			TopDocs tds = searcher.search(query, nums);
			System.out.println("һ����ѯ��:" + tds.totalHits);
			// 6������TopDocs��ȡScoreDoc����
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 7������searcher��ScoreDoc�����ȡ�����Document����
				Document doc = searcher.doc(sd.doc);
				// 8������Document�����ȡ��Ҫ��ֵ
				System.out.println("score:" + sd.score + "--" + sd.doc
						+ "--id:" + doc.get("id") + "--from:" + doc.get("from")
						+ "--name:" + doc.get("name") + "--content:"
						+ doc.get("content") + "--attach:" + doc.get("attach")
						+ "--date:" + doc.get("date"));
			}
			searcher.close();
		} catch (CorruptIndexException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ͨ�������
	 * 
	 * @param field
	 *            Ҫ��������
	 * @param value
	 *            ƥ�������
	 * @param nums
	 *            Ҫ���ҵ��������
	 */
	public void searchByWildCard(String field, String value, int nums) {
		try {
			// 3������IndexReader����IndexSearcher
			IndexSearcher searcher = getSearcher();
			// 4������������Query
			// �ڴ����value�п���ʹ��?��*,����ʾƥ��һ���ַ���*��ʾƥ�����ַ�
			Query query = new WildcardQuery(new Term(field, value));
			// 5������searcher�������ҷ���TopDocs
			TopDocs tds = searcher.search(query, nums);
			System.out.println("һ����ѯ��:" + tds.totalHits);
			// 6������TopDocs��ȡScoreDoc����
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 7������searcher��ScoreDoc�����ȡ�����Document����
				Document doc = searcher.doc(sd.doc);
				// 8������Document�����ȡ��Ҫ��ֵ
				System.out.println("score:" + sd.score + "--" + sd.doc
						+ "--id:" + doc.get("id") + "--from:" + doc.get("from")
						+ "--name:" + doc.get("name") + "--content:"
						+ doc.get("content") + "--attach:" + doc.get("attach")
						+ "--date:" + doc.get("date"));
			}
			searcher.close();
		} catch (CorruptIndexException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void searchByBooleanQuery(int nums) {
		try {
			// 3������IndexReader����IndexSearcher
			IndexSearcher searcher = getSearcher();
			// 4������������Query
			/**
			 * BooleanQuery�������Ӷ���Ӳ�ѯ Occur.MUST ��ʾ������� Occur.SHOULD ��ʾ���Գ���
			 * Occur.MUST_NOT ���ܳ���
			 */
			BooleanQuery query = new BooleanQuery();
			query.add(new TermQuery(new Term("name", "zhangsan")), Occur.MUST);
			query.add(new TermQuery(new Term("content", "nice")), Occur.SHOULD);
			// 5������searcher�������ҷ���TopDocs
			TopDocs tds = searcher.search(query, nums);
			System.out.println("һ����ѯ��:" + tds.totalHits);
			// 6������TopDocs��ȡScoreDoc����
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 7������searcher��ScoreDoc�����ȡ�����Document����
				Document doc = searcher.doc(sd.doc);
				// 8������Document�����ȡ��Ҫ��ֵ
				System.out.println("score:" + sd.score + "--" + sd.doc
						+ "--id:" + doc.get("id") + "--from:" + doc.get("from")
						+ "--name:" + doc.get("name") + "--content:"
						+ doc.get("content") + "--attach:" + doc.get("attach")
						+ "--date:" + doc.get("date"));
			}
			searcher.close();
		} catch (CorruptIndexException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��������,�����ṩ��ͷ�ͽ�β���м��������ķ�ʽ��ѯ��������� i -- football,�м�ĵ���������û��ϵ
	 * 
	 * @param nums
	 */
	public void searchByPhrase(int nums) {
		try {
			// 3������IndexReader����IndexSearcher
			IndexSearcher searcher = getSearcher();
			// 4������������Query
			PhraseQuery query = new PhraseQuery();
			query.setSlop(1);
			query.add(new Term("content", "hi"));
			query.add(new Term("content", "shirt"));
			// 5������searcher�������ҷ���TopDocs
			TopDocs tds = searcher.search(query, nums);
			System.out.println("һ����ѯ��:" + tds.totalHits);
			// 6������TopDocs��ȡScoreDoc����
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 7������searcher��ScoreDoc�����ȡ�����Document����
				Document doc = searcher.doc(sd.doc);
				// 8������Document�����ȡ��Ҫ��ֵ
				System.out.println("score:" + sd.score + "--" + sd.doc
						+ "--id:" + doc.get("id") + "--from:" + doc.get("from")
						+ "--name:" + doc.get("name") + "--content:"
						+ doc.get("content") + "--attach:" + doc.get("attach")
						+ "--date:" + doc.get("date"));
			}
			searcher.close();
		} catch (CorruptIndexException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ģ����ѯ
	 * 
	 * @param field
	 * @param value
	 * @param nums
	 */
	public void searchByFuzzy(String field, String value, int nums) {
		try {
			// 3������IndexReader����IndexSearcher
			IndexSearcher searcher = getSearcher();
			// 4������������Query
			// ����ͨ�����ƶ���������Ҫƥ��ĳ̶�,0.0f��ʾ��ȷ����ͣ����ܴ���1
			Query query = new FuzzyQuery(new Term(field, value), 0.4f, 0);
			// 5������searcher�������ҷ���TopDocs
			TopDocs tds = searcher.search(query, nums);
			System.out.println("һ����ѯ��:" + tds.totalHits);
			// 6������TopDocs��ȡScoreDoc����
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 7������searcher��ScoreDoc�����ȡ�����Document����
				Document doc = searcher.doc(sd.doc);
				// 8������Document�����ȡ��Ҫ��ֵ
				System.out.println("score:" + sd.score + "--" + sd.doc
						+ "--id:" + doc.get("id") + "--from:" + doc.get("from")
						+ "--name:" + doc.get("name") + "--content:"
						+ doc.get("content") + "--attach:" + doc.get("attach")
						+ "--date:" + doc.get("date"));
			}
			searcher.close();
		} catch (CorruptIndexException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ֱ�Ӹ���Query������в�ѯ
	 * 
	 * @param query
	 * @param nums
	 */
	public void searchByQueryParse(Query query, int nums) {
		try {
			// 3������IndexReader����IndexSearcher
			IndexSearcher searcher = getSearcher();
			// 5������searcher�������ҷ���TopDocs
			TopDocs tds = searcher.search(query, nums);
			System.out.println("һ����ѯ��:" + tds.totalHits);
			// 6������TopDocs��ȡScoreDoc����
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 7������searcher��ScoreDoc�����ȡ�����Document����
				Document doc = searcher.doc(sd.doc);
				// 8������Document�����ȡ��Ҫ��ֵ
				System.out.println("score:" + sd.score + "--" + sd.doc
						+ "--id:" + doc.get("id") + "--from:" + doc.get("from")
						+ "--name:" + doc.get("name") + "--content:"
						+ doc.get("content") + "--attach:" + doc.get("attach")
						+ "--date:" + doc.get("date"));
			}
			searcher.close();
		} catch (CorruptIndexException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ҳ��ѯ
	 * 
	 * @param query
	 *            ��ѯ����
	 * @param pageIndex
	 *            �ڼ�ҳ
	 * @param pageSize
	 *            ÿҳ������
	 */
	public void pageSearcher1(String query, int pageIndex, int pageSize) {
		try {
			Directory directory = FileDemoUtil.getDirectory();// ʹ��ͬһ��directory
			IndexSearcher searcher = getSearcher(directory);
			QueryParser parser = new QueryParser(Version.LUCENE_35, "content",
					new StandardAnalyzer(Version.LUCENE_35));
			Query q = parser.parse(query);
			TopDocs tds = searcher.search(q, 1000);
			ScoreDoc[] sds = tds.scoreDocs;
			int start = (pageIndex-1)*pageSize;
			int end = pageIndex*pageSize;
			for(int i=start;i<end;i++){
				Document doc = searcher.doc(sds[i].doc);
				System.out.println(doc.get("path")+doc.get("name"));
			}
			searcher.close();
		} catch (org.apache.lucene.queryParser.ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �õ���һҳ�е����һ��
	 * @param pageIndex
	 * @param pageSize
	 * @param query
	 * @param searcher
	 * @return
	 * @throws Exception
	 */
	public ScoreDoc getLastScoreDoc(int pageIndex, int pageSize,Query query,IndexSearcher searcher) throws Exception{
		if(pageIndex==1){
			return null;
		}
		int results = (pageIndex-1)*pageSize;
		TopDocs tds = searcher.search(query, results);
		return tds.scoreDocs[results-1];
	}
	
	/**
	 * ��ҳ��ѯ
	 * 
	 * @param query
	 *            ��ѯ����
	 * @param pageIndex
	 *            �ڼ�ҳ
	 * @param pageSize
	 *            ÿҳ������
	 */
	public void pageSearcher2(String query, int pageIndex, int pageSize) {
		try {
			Directory directory = FileDemoUtil.getDirectory();// ʹ��ͬһ��directory
			IndexSearcher searcher = getSearcher(directory);
			QueryParser parser = new QueryParser(Version.LUCENE_35, "content",
					new StandardAnalyzer(Version.LUCENE_35));
			Query q = parser.parse(query);
			ScoreDoc after = getLastScoreDoc(pageIndex, pageSize, q, searcher);
			TopDocs tds = searcher.searchAfter(after, q, pageSize);
			for(ScoreDoc sd:tds.scoreDocs){
				Document doc = searcher.doc(sd.doc);
				System.out.println(doc.get("path")+doc.get("name"));
			}
			searcher.close();
		} catch (org.apache.lucene.queryParser.ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
