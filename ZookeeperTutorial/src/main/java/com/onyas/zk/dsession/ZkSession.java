package com.onyas.zk.dsession;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * @author hxpwangyi@163.com
 * @date 2013-3-1
 */
public class ZkSession extends AbstractSession {
	private Map attributes=new HashMap();
	
	public Object getAttribute(String name) {
		return this.attributes.get(name);
	}

	public Object getValue(String name) {
		return null;
	}

	public Enumeration getAttributeNames() {
		return null;
	}

	public String[] getValueNames() {
		return null;
	}

	public void setAttribute(String name, Object value) {
		this.attributes.put(name, value);
		ZkSessionHelper.setAttribute(this.getId(), name, value);
	}

	public void putValue(String name, Object value) {

	}

	public void removeAttribute(String name) {
		this.attributes.remove(name);
		ZkSessionHelper.removeAttribute(this.getId(), name);
	}

	public void removeValue(String name) {

	}

	public void localSetAttribute(String name, Object value){
		this.attributes.put(name, value);
	}

	

}
