package com.tutorial.lucene35;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

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
		} else if (field.equals("time")) {
			String dateType = "yyyy-MM-dd";
			Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
			if (pattern.matcher(part1).matches()
					&& pattern.matcher(part2).matches()) {
				SimpleDateFormat sdf = new SimpleDateFormat(dateType);
				try {
					long start = sdf.parse(part1).getTime();
					long end = sdf.parse(part2).getTime();
					return NumericRangeQuery.newLongRange(field, start, end,
							inclusive, inclusive);
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}

			} else {
				throw new ParseException("日期格式不正确，请使用" + dateType + "的格式");
			}
		}

		return super.getRangeQuery(field, part1, part2, inclusive);
	}
}
