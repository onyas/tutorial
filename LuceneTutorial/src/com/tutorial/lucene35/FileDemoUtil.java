package com.tutorial.lucene35;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

public class FileDemoUtil {

	private static Directory directory = null;

	static {
		try {
			directory = FSDirectory.open(new File("F:/Test/lucene/files"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Directory getDirectory() {
		return directory;
	}

	/**
	 * Ϊ�ļ���������
	 */
	public static void indexFiles() {
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(directory, new IndexWriterConfig(
					Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
			File dir = new File("F:/Test/lucene/txt");
			Document doc = null;
			for (File f : dir.listFiles()) {
				doc = new Document();
				doc.add(new Field("content", new FileReader(f)));
				doc.add(new Field("path", f.getAbsolutePath(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("name", f.getName(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new NumericField("time", Field.Store.YES, false)
						.setLongValue(f.lastModified()));
				doc.add(new NumericField("size", Field.Store.YES, false)
						.setLongValue(f.length() / 1024));
				writer.addDocument(doc);
			}
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
}