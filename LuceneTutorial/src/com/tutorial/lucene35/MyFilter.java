package com.tutorial.lucene35;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.Filter;
import org.apache.lucene.util.OpenBitSet;

public class MyFilter extends Filter {

	private FilterAccessor accessor;
	
	public MyFilter(FilterAccessor accessor) {
		this.accessor = accessor;
	}
	
	@Override
	public DocIdSet getDocIdSet(IndexReader reader) throws IOException {
		//创建一个bit,默认所有的元素都为0
		OpenBitSet obs = new OpenBitSet(reader.maxDoc());
		if(accessor.set()){
			set(reader,obs);
		}else{
			clear(reader,obs);
		}
		return obs;
	}

	public void set(IndexReader reader,OpenBitSet obs){
		try {
			int[] docs = new int[1];
			int[] freqs = new int[1];
			//获取id所在的doc位置,并且将其设置成0
			for(String delId:accessor.getValues()){
				TermDocs tds = reader.termDocs(new Term(accessor.getField(),delId));
				int count = tds.read(docs, freqs);
				if(count==1){
					obs.set(docs[0]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void clear(IndexReader reader,OpenBitSet obs){
		try {
			//先把元素填满,起始位置到结束位置
			obs.set(0, reader.maxDoc()-1);
			int[] docs = new int[1];
			int[] freqs = new int[1];
			//获取id所在的doc位置,并且将其设置成0
			for(String delId:accessor.getValues()){
				TermDocs tds = reader.termDocs(new Term(accessor.getField(),delId));
				int count = tds.read(docs, freqs);
				if(count==1){
					obs.clear(docs[0]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
