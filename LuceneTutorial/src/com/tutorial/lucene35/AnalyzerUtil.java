package com.tutorial.lucene35;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class AnalyzerUtil {

	public static void displayToken(String str, Analyzer analyzer) {
		try {
			TokenStream stream = analyzer.tokenStream("content", new StringReader(
					str));
			//����һ�����ԣ�������Ի�������У��������TokenStream����
			CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
			while(stream.incrementToken()){
				System.out.print("["+cta+"]");
			}
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
