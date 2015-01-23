package com.onyas.zk.dsession;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * @author hxpwangyi@163.com
 * @date 2013-3-1
 */
public abstract class SessionChangeListener implements ServletContextListener {
	private static long timeout;
	
	public static long getTimeout(){
		return timeout;
	}
	
	public void contextInitialized(ServletContextEvent sce) {
		String timeoutStr=sce.getServletContext().getInitParameter("sessionTimeout");
		if(timeoutStr!=null&&!timeoutStr.equals("")){
			timeout=Long.parseLong(timeoutStr);
		}else{
			timeout=3600000;
		}
		subscribeSession();
	}

	public void contextDestroyed(ServletContextEvent sce) {
		release();
	}
	
	protected abstract void subscribeSession();
	
	protected abstract void release();
}
