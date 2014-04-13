package com.tutorial.lucene35;

import java.io.Reader;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LetterTokenizer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.util.Version;

public class MyStopAnalyzer extends Analyzer {

	// 用来存储停用词
	private Set stops;

	// 将sws数组中的单词屏蔽掉
	public MyStopAnalyzer(String[] sws) {
		// 自动将字符串数组转换成set
		stops = StopFilter.makeStopSet(Version.LUCENE_35, sws, true);
		// 把默认停用诩加入自定义的停用词集合中
		stops.addAll(StopAnalyzer.ENGLISH_STOP_WORDS_SET);
	}

	// 默认情况下跟StopAnalyzer效果一样
	public MyStopAnalyzer() {
		stops = StopAnalyzer.ENGLISH_STOP_WORDS_SET;
	}

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {

		// Tokenier负责将相应的一组数据转换为一个个的语汇单元
		// 用一系列的TokenFilter将分好词的数据进行过滤操作
		return new StopFilter(Version.LUCENE_35, new LowerCaseFilter(
				Version.LUCENE_35, new LetterTokenizer(Version.LUCENE_35,
						reader)), stops);

	}

}
