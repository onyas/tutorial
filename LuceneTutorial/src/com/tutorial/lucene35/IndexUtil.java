package com.tutorial.lucene35;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
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

	private Directory directory = null;

	public IndexUtil() {
		try {
			directory = FSDirectory.open(new File("F:/Test/lucene/index02"));
		} catch (IOException e) {
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
			IndexReader reader = IndexReader.open(directory,false);
			reader.undeleteAll();
			reader.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
