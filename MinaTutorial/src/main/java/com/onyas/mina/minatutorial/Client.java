package com.onyas.mina.minatutorial;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class Client {
	private static Logger logger = Logger.getLogger(Client.class);

	private static String HOST = "127.0.0.1";

	private static int PORT = 3005;

	public static void main(String[] args) {
		// 创建一个非阻塞的客户端程序
		IoConnector connector = new NioSocketConnector();
		// 设置链接超时时间
		connector.setConnectTimeout(30000);
		// 添加过滤器
		connector.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset
						.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(),
						LineDelimiter.WINDOWS.getValue())));
		// 添加业务逻辑处理器类
		connector.setHandler(new ClientHandler());
		IoSession session = null;
		try {
			ConnectFuture future = connector.connect(new InetSocketAddress(
					HOST, PORT));// 创建连接
			future.awaitUninterruptibly();// 等待连接创建完成
			session = future.getSession();// 获得session
			
			Map<Long,IoSession> map =session.getService().getManagedSessions();
			logger.info("当前有"+map.size()+"个连接,ID分为:");
			Iterator<Entry<Long, IoSession>> iterator = map.entrySet().iterator();
			while(iterator.hasNext()){
				Entry<Long, IoSession> entry = iterator.next();
				logger.info("键为:"+entry.getKey()+",值为:"+entry.getValue());
			}
			
			session.write("测试mina");// 发送消息
		} catch (Exception e) {
			logger.error("客户端链接异常...", e);
		}

		session.getCloseFuture().awaitUninterruptibly();// 等待连接断开
		connector.dispose();
	}
}