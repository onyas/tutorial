package com.onyas.mina.minatutorial;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class ServerHandler extends IoHandlerAdapter {
	public static Logger logger = Logger.getLogger(ServerHandler.class);

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.info("服务端与客户端创建连接...");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.info("服务端与客户端连接打开...");
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String msg = message.toString();
		logger.info("服务端接收到的数据为：" + msg);
		logger.info("当前连接的IoSession ID为:"+session.getId());
		if ("bye".equals(msg)) { // 服务端断开连接的条件
			session.close();
		}
		Map map = session.getService().getManagedSessions();
		Date date = new Date();
		session.write(date);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		logger.info("服务端发送信息成功...");
		//服务端发送成功后，立即关闭连接，即为 短连接
//		session.close();
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {

	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		logger.info("服务端进入空闲状态...");
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		logger.error("服务端发送异常...", cause);
	}
}