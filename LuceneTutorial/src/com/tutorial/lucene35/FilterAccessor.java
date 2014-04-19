package com.tutorial.lucene35;

public interface FilterAccessor {

	/**
	 * 得到所要处理的数据
	 * @return
	 */
	public String[] getValues();
	
	/**
	 * 对哪个域进行处理
	 * @return
	 */
	public String getField();
	
	/**
	 * 是否要显示所设置的位置，或数据
	 * @return
	 */
	public boolean set();
	
}
