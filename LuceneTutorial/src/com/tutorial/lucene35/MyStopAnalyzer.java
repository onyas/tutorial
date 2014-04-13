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

	// �����洢ͣ�ô�
	private Set stops;

	// ��sws�����еĵ������ε�
	public MyStopAnalyzer(String[] sws) {
		// �Զ����ַ�������ת����set
		stops = StopFilter.makeStopSet(Version.LUCENE_35, sws, true);
		// ��Ĭ��ͣ��ڼ�����Զ����ͣ�ôʼ�����
		stops.addAll(StopAnalyzer.ENGLISH_STOP_WORDS_SET);
	}

	// Ĭ������¸�StopAnalyzerЧ��һ��
	public MyStopAnalyzer() {
		stops = StopAnalyzer.ENGLISH_STOP_WORDS_SET;
	}

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {

		// Tokenier������Ӧ��һ������ת��Ϊһ��������㵥Ԫ
		// ��һϵ�е�TokenFilter���ֺôʵ����ݽ��й��˲���
		return new StopFilter(Version.LUCENE_35, new LowerCaseFilter(
				Version.LUCENE_35, new LetterTokenizer(Version.LUCENE_35,
						reader)), stops);

	}

}
