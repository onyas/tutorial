package com.test.tutorial.lucene35;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.junit.Test;

import com.tutorial.lucene35.AnalyzerUtil;

public class TestAnalyzerUtil {

	@Test
	public void testDisplayToken(){
		Analyzer a1 = new StandardAnalyzer(Version.LUCENE_35);
		Analyzer a2 = new StopAnalyzer(Version.LUCENE_35);
		Analyzer a3 = new SimpleAnalyzer(Version.LUCENE_35);
		Analyzer a4 = new WhitespaceAnalyzer(Version.LUCENE_35);
		
		String str = "hello this is just a test,so dont't be so serroul,ok?";
//		String str ="这只是一个测试";
		AnalyzerUtil.displayToken(str, a1);
		AnalyzerUtil.displayToken(str, a2);
		AnalyzerUtil.displayToken(str, a3);
		AnalyzerUtil.displayToken(str, a4);
	}
	
	
	@Test
	public void testDisplayAllToken(){
		Analyzer a1 = new StandardAnalyzer(Version.LUCENE_35);
		Analyzer a2 = new StopAnalyzer(Version.LUCENE_35);
		Analyzer a3 = new SimpleAnalyzer(Version.LUCENE_35);
		Analyzer a4 = new WhitespaceAnalyzer(Version.LUCENE_35);
		
		String str = "hello this is just a test";
//		String str ="这只是一个测试";
		AnalyzerUtil.displayAllToken(str, a1);
		AnalyzerUtil.displayAllToken(str, a2);
		AnalyzerUtil.displayAllToken(str, a3);
		AnalyzerUtil.displayAllToken(str, a4);
	}
}
