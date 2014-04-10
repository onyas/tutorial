package com.tutorial.lucene35;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

public class FirstLucene {

	/**
	 * 建立索引 1、创建Directory 2、创建IndexWriter 3、创建Document对象 4、为Document添加Field
	 * 5、通过IndexWriter添加文档到索引中
	 */
	public void index() {
		// 1、创建Directory
		Directory directory = null;
		IndexWriter writer = null;
		try {
			// directory = new RAMDirectory();//建立在内存中
			directory = FSDirectory.open(new File("F:/Test/lucene/index01"));// 建立在硬盘上
			// 2、创建IndexWriter
			IndexWriterConfig writerconfig = new IndexWriterConfig(
					Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35));
			writer = new IndexWriter(directory, writerconfig);
			// 3、创建Document对象
			Document doc = null;
			File dir = new File("f:/Test/lucene/txt");
			for (File file : dir.listFiles()) {
				doc = new Document();
				// 4、为Document添加Field
				doc.add(new Field("content", new FileReader(file)));
				doc.add(new Field("filename", file.getName(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("path", file.getAbsolutePath(),
						Field.Store.YES, Field.Index.NOT_ANALYZED));
				// 5、通过IndexWriter添加文档到索引中
				writer.addDocument(doc);
			}
		} catch (CorruptIndexException e1) {
			e1.printStackTrace();
		} catch (LockObtainFailedException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
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
	 * 搜索 1、创建Directory 2、创建IndexReader 3、根据IndexReader创建IndexSearcher
	 * 4、创建搜索的Query 5、根据searcher搜索并且返回TopDocs 6、根据TopDocs获取ScoreDoc对象
	 * 7、根据searcher和ScoreDoc对象获取具体的Document对象 8、根据Document对象获取需要的值 9、关闭reader
	 */
	public void searcher() {

		try {
			// 1、创建Directory
			Directory directory = FSDirectory.open(new File(
					"F:/Test/lucene/index01"));
			// 2、创建IndexReader
			IndexReader reader = IndexReader.open(directory);
			// 3、根据IndexReader创建IndexSearcher
			IndexSearcher searcher = new IndexSearcher(reader);
			// 4、创建搜索的Query
			// 创建parser来确定要搜索文件的内容，第二个参数表示搜索的域
			QueryParser parser = new QueryParser(Version.LUCENE_35, "content",
					new StandardAnalyzer(Version.LUCENE_35));
			// 创建query,表示搜索域为content中包含java的文档
			Query query = parser.parse("String");
			// 5、根据searcher搜索并且返回TopDocs
			TopDocs tds = searcher.search(query, 10);
			// 6、根据TopDocs获取ScoreDoc对象
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 7、根据searcher和ScoreDoc对象获取具体的Document对象
				Document doc = searcher.doc(sd.doc);
				// 8、根据Document对象获取需要的值
				System.out.println(doc.get("filename") + "[" + doc.get("path")
						+ "]");
			}
			// 9、关闭reader
			reader.clone();
		} catch (CorruptIndexException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
}
