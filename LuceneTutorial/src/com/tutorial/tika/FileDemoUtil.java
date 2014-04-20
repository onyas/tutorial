package com.tutorial.tika;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
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
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;


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

	public static Document generatorDoc(File f) throws IOException {
		Document doc = new Document();
		Metadata metadata = new Metadata();

		// 通过tika直接存储
		doc.add(new Field("content", new Tika().parse(new FileInputStream(f),
				metadata)));
		int pages = 0;
		try {
			pages = Integer.parseInt(metadata.get("xmpTPg:NPages"));
		} catch (NumberFormatException e) {
		}
		doc.add(new NumericField("pages", Field.Store.YES, true).setIntValue(pages));
		doc.add(new Field("title", FilenameUtils.getBaseName(f.getName()),
				Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
		doc.add(new Field("type", FilenameUtils.getExtension(f.getName()),
				Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
		doc.add(new Field("path", f.getAbsolutePath(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("name", f.getName(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new NumericField("time", Field.Store.YES, true).setLongValue(f
				.lastModified()));
		doc.add(new NumericField("size", Field.Store.YES, true).setLongValue(f
				.length()));

		return doc;
	}

	/**
	 * 用tika进行索引
	 */
	public static void indexFiles(boolean delAll) {
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(directory, new IndexWriterConfig(
					Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
			if (delAll) {
				writer.deleteAll();
			}
			File dir = new File("F:/Test/lucene/txt");
			Document doc = null;
			for (File f : dir.listFiles()) {
				doc = generatorDoc(f);
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
