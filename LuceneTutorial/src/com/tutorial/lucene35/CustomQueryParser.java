package com.tutorial.lucene35;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.NumericRangeQuery;
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

	@Override
	protected org.apache.lucene.search.Query getRangeQuery(String field,
			String part1, String part2, boolean inclusive)
			throws ParseException {

		if (field.equals("size")) {
			return NumericRangeQuery.newLongRange(field, Long.parseLong(part1),
					Long.parseLong(part2), inclusive, inclusive);
		}

		return super.getRangeQuery(field, part1, part2, inclusive);
	}
}
