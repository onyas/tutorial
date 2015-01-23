package com.onyas.zk.dsession;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hxpwangyi@163.com
 * @date 2013-3-1
 */
public abstract class AbstractSessionFilter implements Filter{

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		doFilterInternal((HttpServletRequest)request,(HttpServletResponse)response);
		chain.doFilter(request, response);
	}
	
	protected abstract void doFilterInternal(HttpServletRequest request,HttpServletResponse response);

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
