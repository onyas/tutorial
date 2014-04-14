package com.tutorial.lucene35;

import java.util.HashMap;
import java.util.Map;

public class SimpleSynonymContext implements SynonymContext {

	Map<String, String[]> maps = new HashMap<String, String[]>();

	public SimpleSynonymContext() {
		maps.put("中国", new String[] { "大陆", "天朝" });
		maps.put("我", new String[] { "咱", "俺" });
	}

	@Override
	public String[] getSynonym(String name) {

		return maps.get(name);
	}

}
