package com.tutorial.lucene35;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
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
}
