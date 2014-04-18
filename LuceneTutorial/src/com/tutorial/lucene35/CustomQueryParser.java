package com.tutorial.lucene35;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.util.Version;

public class CustomQueryParser extends QueryParser {

	public CustomQueryParser(Version matchVersion, String f, Analyzer a) {
		super(matchVersion, f, a);
	}

	@Override
	protected org.apache.lucene.search.Query getWildcardQuery(String field,
			String termStr) throws ParseException {
		throw new ParseException("因为性能原因，不能使用通配符查询");
	}
	
	
	@Override
	protected org.apache.lucene.search.Query getFuzzyQuery(String field,
			String termStr, float minSimilarity) throws ParseException {
		throw new ParseException("因为性能原因，不能使用模糊查询");
	}
	
}
