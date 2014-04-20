package com.tutorial.highlighter;

import java.io.IOException;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.util.Version;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;

public class HighlighterUtil {

	public void light() {
		try {
			String txt = "我爱北京天安门，天安门上彩旗飞，伟大领袖毛泽东，指引我们向前进，向前时，基本原则加盟在时枥基本原则加枯困在粗俗基本原则满腔热情， 洋儿童有量罟";
			Query query = new QueryParser(Version.LUCENE_35, "content",
					new MMSegAnalyzer()).parse("北京 伟大");
			QueryScorer scorer = new QueryScorer(query);
			Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
			Highlighter lighter = new Highlighter(scorer);
			lighter.setTextFragmenter(fragmenter);
			String str = lighter.getBestFragment(new MMSegAnalyzer(), "content", txt);
			System.out.println(str);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidTokenOffsetsException e) {
			e.printStackTrace();
		}
		
	}

}
