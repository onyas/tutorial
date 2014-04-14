package com.test.tutorial.lucene35;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexReader.FieldOption;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;
import com.chenlb.mmseg4j.analysis.MaxWordAnalyzer;
import com.tutorial.lucene35.AnalyzerUtil;
import com.tutorial.lucene35.MyStopAnalyzer;
import com.tutorial.lucene35.MySynonymAnalyzer;

public class TestAnalyzerUtil {

	@Test
	public void testDisplayToken() {
		Analyzer a1 = new StandardAnalyzer(Version.LUCENE_35);
		Analyzer a2 = new StopAnalyzer(Version.LUCENE_35);
		Analyzer a3 = new SimpleAnalyzer(Version.LUCENE_35);
		Analyzer a4 = new WhitespaceAnalyzer(Version.LUCENE_35);

		String str = "hello this is just a test,so dont't be so serroul,ok?";
		// String str ="杩欏彧鏄竴涓祴璇�;
		AnalyzerUtil.displayToken(str, a1);
		AnalyzerUtil.displayToken(str, a2);
		AnalyzerUtil.displayToken(str, a3);
		AnalyzerUtil.displayToken(str, a4);
	}

	@Test
	public void testDisplayAllToken() {
		Analyzer a1 = new StandardAnalyzer(Version.LUCENE_35);
		Analyzer a2 = new StopAnalyzer(Version.LUCENE_35);
		Analyzer a3 = new SimpleAnalyzer(Version.LUCENE_35);
		Analyzer a4 = new WhitespaceAnalyzer(Version.LUCENE_35);

		String str = "hello this is just a test";
		// String str ="杩欏彧鏄竴涓祴璇�;
		AnalyzerUtil.displayAllToken(str, a1);
		AnalyzerUtil.displayAllToken(str, a2);
		AnalyzerUtil.displayAllToken(str, a3);
		AnalyzerUtil.displayAllToken(str, a4);
	}

	@Test
	public void testMyStopAnalyzer() {
		Analyzer a1 = new MyStopAnalyzer(new String[] { "is", "a", "hello" });
		Analyzer a2 = new StopAnalyzer(Version.LUCENE_35);

		String str = "hello this is just a test";
		AnalyzerUtil.displayToken(str, a1);
		AnalyzerUtil.displayToken(str, a2);
	}

	@Test
	public void testMySynonymAnalyzer() {
		Analyzer a1 = new MySynonymAnalyzer();

		String str = "我来自中国福建福州";
		AnalyzerUtil.displayToken(str, a1);
	}

	@Test
	public void testMmseg() {
		Analyzer a1 = new com.chenlb.mmseg4j.analysis.SimpleAnalyzer();
		Analyzer a2 = new ComplexAnalyzer();
		Analyzer a3 = new MaxWordAnalyzer();
		Analyzer a4 = new MMSegAnalyzer();

		String str = "我来自中国福建福州";

		AnalyzerUtil.displayToken(str, a1);
		AnalyzerUtil.displayToken(str, a2);
		AnalyzerUtil.displayToken(str, a3);
		AnalyzerUtil.displayToken(str, a4);
	}

	@Test
	public void testBaseSynonymAnalyzer() {
		try {
			Analyzer a1 = new MySynonymAnalyzer();
			String txt = "我来自中国福建福州";
			Directory dir = new RAMDirectory();
			IndexWriter writer = new IndexWriter(dir, new IndexWriterConfig(
					Version.LUCENE_35, a1));
			Document doc = new Document();
			doc.add(new Field("content", txt, Field.Store.YES,
					Field.Index.ANALYZED));
			writer.addDocument(doc);
			writer.close();
			IndexSearcher searcher = new IndexSearcher(IndexReader.open(dir));
			// 可以改变搜索的内容，比如"大陆"，"天朝"等
			TopDocs tds = searcher.search(new TermQuery(new Term("content",
					"天朝")), 20);
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				Document document = searcher.doc(sd.doc);
				System.out.println(document.get("content"));
			}
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
