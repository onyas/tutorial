package com.onyas.zk.dsession;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * @author hxpwangyi@163.com
 * @date 2013-3-1
 */
public abstract class AbstractSession implements HttpSession {
	private SessionMetaData meta;

	public AbstractSession(){
		meta=new SessionMetaData();
		meta.setCreateTime(new Date().getTime());
		//meta.setSid(SidGenerator.generateSid());
		meta.setLastAccessedTime(meta.getCreateTime());
		meta.setIsnew(true);
		meta.setMaxInactiveInterval((int)SessionChangeListener.getTimeout());
		//ZkSessionHelper.addSession(meta);
	}

	public void setLastAccessedTime(long lastAccessedTime) {
		meta.setLastAccessedTime(lastAccessedTime);
	}

	public long getCreationTime() {
		return meta.getCreateTime();
	}

	public String getId() {
		return meta.getSid();
	}
	
	public void setId(String sid){
		meta.setSid(sid);
	}

	public long getLastAccessedTime() {
		return meta.getLastAccessedTime();
	}

	public ServletContext getServletContext() {
		return null;
	}

	public void setMaxInactiveInterval(int interval) {
		meta.setMaxInactiveInterval(interval);

	}

	public int getMaxInactiveInterval() {
		return meta.getMaxInactiveInterval();
	}

	public HttpSessionContext getSessionContext() {
		return null;
	}
	
	public void invalidate() {

	}

	public boolean isNew() {
		return meta.isIsnew();
	}

	public SessionMetaData getMeta() {
		return meta;
	}

	public void setMeta(SessionMetaData meta) {
		this.meta = meta;
	}
	
	
}
