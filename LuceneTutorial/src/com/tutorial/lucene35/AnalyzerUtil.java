package com.tutorial.lucene35;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

public class AnalyzerUtil {

	public static void displayToken(String str, Analyzer analyzer) {
		try {
			TokenStream stream = analyzer.tokenStream("content",
					new StringReader(str));
			// 创建一个属性，这个属性会添加流中，随着这个TokenStream增加
			CharTermAttribute cta = stream
					.addAttribute(CharTermAttribute.class);
			while (stream.incrementToken()) {
				System.out.print("[" + cta + "]");
			}
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void displayAllToken(String str, Analyzer analyzer) {
		try {
			TokenStream stream = analyzer.tokenStream("content",
					new StringReader(str));
			// 创建一个属性，这个属性会添加流中，随着这个TokenStream增加
			CharTermAttribute cta = stream
					.addAttribute(CharTermAttribute.class);
			PositionIncrementAttribute pia = stream
					.addAttribute(PositionIncrementAttribute.class);
			OffsetAttribute oa = stream.addAttribute(OffsetAttribute.class);
			TypeAttribute ta = stream.addAttribute(TypeAttribute.class);
			while (stream.incrementToken()) {
				System.out.print("position:" + pia.getPositionIncrement());
				System.out.println(cta+"["+oa.startOffset() + "--" + oa.endOffset()
						+ "]" + ta.type());
			}
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
