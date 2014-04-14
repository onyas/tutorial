package com.tutorial.lucene35;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.util.AttributeSource;

public class MySynonymFilter extends TokenFilter {

	private CharTermAttribute cta = null;
	private PositionIncrementAttribute pia = null;
	private Stack<String> synonyms=null;
	private AttributeSource.State current;

	protected MySynonymFilter(TokenStream input) {
		super(input);
		cta = this.addAttribute(CharTermAttribute.class);
		pia = this.addAttribute(PositionIncrementAttribute.class);
		synonyms = new Stack<String>();
	}

	@Override
	public boolean incrementToken() throws IOException {
		
		while(synonyms.size()>0){
			//将元素出栈，并且获取这个元素
			String str = synonyms.pop();
			//还原状态
			restoreState(current);
			cta.setEmpty();
			cta.append(str);
			return true;
		}
		
		if(!this.input.incrementToken()){
			return false;
		}
		
		if(getSynonymWords(cta.toString())){
			//如果有同义词，将当前状态保存
			current = captureState();
		}
		
		return true;
	}

	/**
	 * 检查是否有同义词
	 * @param name
	 * @return
	 */
	private boolean getSynonymWords(String name){
		Map<String,String[]> maps = new HashMap<String,String[]>();
		maps.put("中国",new String[]{"大陆","天朝"});
		maps.put("我",new String[]{"咱","俺"});
		String[] sws = maps.get(name);
		if(sws!=null){
			for(String s:sws){
				synonyms.push(s);
			}
			return true;
		}
		return false;
	}
	
}
