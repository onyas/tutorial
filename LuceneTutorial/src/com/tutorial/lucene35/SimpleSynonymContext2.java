package com.tutorial.lucene35;

import java.util.HashMap;
import java.util.Map;

public class SimpleSynonymContext2 implements SynonymContext {

	Map<String, String[]> maps = new HashMap<String, String[]>();

	public SimpleSynonymContext2() {
		maps.put("中国", new String[] { "大陆", "天朝" });
	}

	@Override
	public String[] getSynonym(String name) {

		return maps.get(name);
	}

}
