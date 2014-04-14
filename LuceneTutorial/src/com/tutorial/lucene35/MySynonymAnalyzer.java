package com.tutorial.lucene35;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MaxWordSeg;
import com.chenlb.mmseg4j.analysis.MMSegTokenizer;

public class MySynonymAnalyzer extends Analyzer {

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		Dictionary dic = Dictionary.getInstance("F:\\OpenSource\\Java\\lucene\\mmseg4j-1.8.3\\data");
		return new MySynonymFilter(new MMSegTokenizer(new MaxWordSeg(dic), reader));
	}

}
