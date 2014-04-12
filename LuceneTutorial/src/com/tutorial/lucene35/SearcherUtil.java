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
	 * 得到IndexSearcher
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
				doc.add(new NumericField("attach", Field.Store.YES, true)
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
	 * 
	 * @param field
	 *            要查找的域
	 * @param name
	 *            域中要查找的名字
	 * @param nums
	 *            查找的数量
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
	 * 范围搜索
	 * 
	 * @param field
	 *            要查找的域
	 * @param lower
	 *            起始位置
	 * @param upper
	 *            结束位置
	 * @param nums
	 *            结果数量
	 */
	public void searchByTermRange(String field, String lower, String upper,
			int nums) {
		try {
			// 3、根据IndexReader创建IndexSearcher
			IndexSearcher searcher = getSearcher();
			// 4、创建搜索的Query
			Query query = new TermRangeQuery(field, lower, upper, true, true);
			// 5、根据searcher搜索并且返回TopDocs
			TopDocs tds = searcher.search(query, nums);
			System.out.println("一共查询到:" + tds.totalHits);
			// 6、根据TopDocs获取ScoreDoc对象
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 7、根据searcher和ScoreDoc对象获取具体的Document对象
				Document doc = searcher.doc(sd.doc);
				// 8、根据Document对象获取需要的值
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
	 * 数字范围的搜索
	 * 
	 * @param field
	 *            要查找的域
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
	 * @param nums
	 *            查找的数量
	 */
	public void searchByNumericRangeQuery(String field, int min, int max,
			int nums) {
		try {
			// 3、根据IndexReader创建IndexSearcher
			IndexSearcher searcher = getSearcher();
			// 4、创建搜索的Query
			Query query = NumericRangeQuery.newIntRange(field, min, max, true,
					true);

			// 5、根据searcher搜索并且返回TopDocs
			TopDocs tds = searcher.search(query, nums);
			System.out.println("一共查询到:" + tds.totalHits);
			// 6、根据TopDocs获取ScoreDoc对象
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 7、根据searcher和ScoreDoc对象获取具体的Document对象
				Document doc = searcher.doc(sd.doc);
				// 8、根据Document对象获取需要的值
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
	 * 前缀搜索
	 * 
	 * @param field
	 *            要搜索的域
	 * @param value
	 *            匹配的名字
	 * @param nums
	 *            要查找的最大数量
	 */
	public void searchByPrefix(String field, String value, int nums) {
		try {
			// 3、根据IndexReader创建IndexSearcher
			IndexSearcher searcher = getSearcher();
			// 4、创建搜索的Query
			Query query = new PrefixQuery(new Term(field, value));
			// 5、根据searcher搜索并且返回TopDocs
			TopDocs tds = searcher.search(query, nums);
			System.out.println("一共查询到:" + tds.totalHits);
			// 6、根据TopDocs获取ScoreDoc对象
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 7、根据searcher和ScoreDoc对象获取具体的Document对象
				Document doc = searcher.doc(sd.doc);
				// 8、根据Document对象获取需要的值
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
	 * 通配符搜索
	 * 
	 * @param field
	 *            要搜索的域
	 * @param value
	 *            匹配的名字
	 * @param nums
	 *            要查找的最大数量
	 */
	public void searchByWildCard(String field, String value, int nums) {
		try {
			// 3、根据IndexReader创建IndexSearcher
			IndexSearcher searcher = getSearcher();
			// 4、创建搜索的Query
			// 在传入的value中可以使用?和*,？表示匹配一个字符，*表示匹配多个字符
			Query query = new WildcardQuery(new Term(field, value));
			// 5、根据searcher搜索并且返回TopDocs
			TopDocs tds = searcher.search(query, nums);
			System.out.println("一共查询到:" + tds.totalHits);
			// 6、根据TopDocs获取ScoreDoc对象
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 7、根据searcher和ScoreDoc对象获取具体的Document对象
				Document doc = searcher.doc(sd.doc);
				// 8、根据Document对象获取需要的值
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
			// 3、根据IndexReader创建IndexSearcher
			IndexSearcher searcher = getSearcher();
			// 4、创建搜索的Query
			/**
			 * BooleanQuery可以连接多个子查询 Occur.MUST 表示必须出现 Occur.SHOULD 表示可以出现
			 * Occur.MUST_NOT 不能出现
			 */
			BooleanQuery query = new BooleanQuery();
			query.add(new TermQuery(new Term("name", "zhangsan")), Occur.MUST);
			query.add(new TermQuery(new Term("content", "nice")), Occur.SHOULD);
			// 5、根据searcher搜索并且返回TopDocs
			TopDocs tds = searcher.search(query, nums);
			System.out.println("一共查询到:" + tds.totalHits);
			// 6、根据TopDocs获取ScoreDoc对象
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 7、根据searcher和ScoreDoc对象获取具体的Document对象
				Document doc = searcher.doc(sd.doc);
				// 8、根据Document对象获取需要的值
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
	 * 短语搜索,可以提供开头和结尾，中间用跳过的方式查询，比如查找 i -- football,中间的单词忘记了没关系
	 * 
	 * @param nums
	 */
	public void searchByPhrase(int nums) {
		try {
			// 3、根据IndexReader创建IndexSearcher
			IndexSearcher searcher = getSearcher();
			// 4、创建搜索的Query
			PhraseQuery query = new PhraseQuery();
			query.setSlop(1);
			query.add(new Term("content", "hi"));
			query.add(new Term("content", "shirt"));
			// 5、根据searcher搜索并且返回TopDocs
			TopDocs tds = searcher.search(query, nums);
			System.out.println("一共查询到:" + tds.totalHits);
			// 6、根据TopDocs获取ScoreDoc对象
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 7、根据searcher和ScoreDoc对象获取具体的Document对象
				Document doc = searcher.doc(sd.doc);
				// 8、根据Document对象获取需要的值
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
	 * 模糊查询
	 * 
	 * @param field
	 * @param value
	 * @param nums
	 */
	public void searchByFuzzy(String field, String value, int nums) {
		try {
			// 3、根据IndexReader创建IndexSearcher
			IndexSearcher searcher = getSearcher();
			// 4、创建搜索的Query
			// 可以通过相似度来控制所要匹配的程度,0.0f表示精确度最低，不能大于1
			Query query = new FuzzyQuery(new Term(field, value), 0.4f, 0);
			// 5、根据searcher搜索并且返回TopDocs
			TopDocs tds = searcher.search(query, nums);
			System.out.println("一共查询到:" + tds.totalHits);
			// 6、根据TopDocs获取ScoreDoc对象
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 7、根据searcher和ScoreDoc对象获取具体的Document对象
				Document doc = searcher.doc(sd.doc);
				// 8、根据Document对象获取需要的值
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
	 * 直接跟据Query对象进行查询
	 * 
	 * @param query
	 * @param nums
	 */
	public void searchByQueryParse(Query query, int nums) {
		try {
			// 3、根据IndexReader创建IndexSearcher
			IndexSearcher searcher = getSearcher();
			// 5、根据searcher搜索并且返回TopDocs
			TopDocs tds = searcher.search(query, nums);
			System.out.println("一共查询到:" + tds.totalHits);
			// 6、根据TopDocs获取ScoreDoc对象
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 7、根据searcher和ScoreDoc对象获取具体的Document对象
				Document doc = searcher.doc(sd.doc);
				// 8、根据Document对象获取需要的值
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
	 * 分页查询
	 * 
	 * @param query
	 *            查询条件
	 * @param pageIndex
	 *            第几页
	 * @param pageSize
	 *            每页多少条
	 */
	public void pageSearcher1(String query, int pageIndex, int pageSize) {
		try {
			Directory directory = FileDemoUtil.getDirectory();// 使用同一个directory
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
	 * 得到上一页中的最后一个
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
	 * 分页查询
	 * 
	 * @param query
	 *            查询条件
	 * @param pageIndex
	 *            第几页
	 * @param pageSize
	 *            每页多少条
	 */
	public void pageSearcher2(String query, int pageIndex, int pageSize) {
		try {
			Directory directory = FileDemoUtil.getDirectory();// 使用同一个directory
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
