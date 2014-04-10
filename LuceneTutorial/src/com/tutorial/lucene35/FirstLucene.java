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
	 * �������� 1������Directory 2������IndexWriter 3������Document���� 4��ΪDocument���Field
	 * 5��ͨ��IndexWriter����ĵ���������
	 */
	public void index() {
		// 1������Directory
		Directory directory = null;
		IndexWriter writer = null;
		try {
			// directory = new RAMDirectory();//�������ڴ���
			directory = FSDirectory.open(new File("F:/Test/lucene/index01"));// ������Ӳ����
			// 2������IndexWriter
			IndexWriterConfig writerconfig = new IndexWriterConfig(
					Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35));
			writer = new IndexWriter(directory, writerconfig);
			// 3������Document����
			Document doc = null;
			File dir = new File("f:/Test/lucene/txt");
			for (File file : dir.listFiles()) {
				doc = new Document();
				// 4��ΪDocument���Field
				doc.add(new Field("content", new FileReader(file)));
				doc.add(new Field("filename", file.getName(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("path", file.getAbsolutePath(),
						Field.Store.YES, Field.Index.NOT_ANALYZED));
				// 5��ͨ��IndexWriter����ĵ���������
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
	 * ���� 1������Directory 2������IndexReader 3������IndexReader����IndexSearcher
	 * 4������������Query 5������searcher�������ҷ���TopDocs 6������TopDocs��ȡScoreDoc����
	 * 7������searcher��ScoreDoc�����ȡ�����Document���� 8������Document�����ȡ��Ҫ��ֵ 9���ر�reader
	 */
	public void searcher() {

		try {
			// 1������Directory
			Directory directory = FSDirectory.open(new File(
					"F:/Test/lucene/index01"));
			// 2������IndexReader
			IndexReader reader = IndexReader.open(directory);
			// 3������IndexReader����IndexSearcher
			IndexSearcher searcher = new IndexSearcher(reader);
			// 4������������Query
			// ����parser��ȷ��Ҫ�����ļ������ݣ��ڶ���������ʾ��������
			QueryParser parser = new QueryParser(Version.LUCENE_35, "content",
					new StandardAnalyzer(Version.LUCENE_35));
			// ����query,��ʾ������Ϊcontent�а���java���ĵ�
			Query query = parser.parse("String");
			// 5������searcher�������ҷ���TopDocs
			TopDocs tds = searcher.search(query, 10);
			// 6������TopDocs��ȡScoreDoc����
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 7������searcher��ScoreDoc�����ȡ�����Document����
				Document doc = searcher.doc(sd.doc);
				// 8������Document�����ȡ��Ҫ��ֵ
				System.out.println(doc.get("filename") + "[" + doc.get("path")
						+ "]");
			}
			// 9���ر�reader
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
