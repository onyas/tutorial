package com.onyas.zk.dsession;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author hxpwangyi@163.com
 * @date 2013-3-1
 */
public class ZkSessionFilter extends AbstractSessionFilter {

	
	private void newSession(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=new ZkSession();
		String sid=SidGenerator.generateSid();
		((AbstractSession)session).setId(sid);
		ZkSessionManager.getInstance().addSession(session, sid);
		ZkSessionHelper.addSession(((AbstractSession)session).getMeta());
		Cookie cookie=new Cookie("sid",sid);
		cookie.setMaxAge((int)SessionChangeListener.getTimeout());
		response.addCookie(cookie);
		request.setAttribute("sid", sid);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response) {
		AbstractSessionManager sessionManager=ZkSessionManager.getInstance();
		String sid=sessionManager.getSessionIdByCookie((HttpServletRequest)request);
		if(sid==null || sid.equals("")){
			newSession((HttpServletRequest)request,(HttpServletResponse)response);
		}else{
			AbstractSession session=(AbstractSession)sessionManager.getSession(sid);
			if(session!=null){
				session.setLastAccessedTime(new Date().getTime());
				ZkClient client=ZkConnectionSingleton.getInstance();
				client.writeData(ZkSessionHelper.root+"/"+sid, session.getMeta());
			}else{
				newSession((HttpServletRequest)request,(HttpServletResponse)response);
			}
		}
	}

}
