package com.onyas.mina.filetransferused.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;


public class FileServer {

	private static Logger logger = Logger.getLogger(FileServer.class);
	
	private IoAcceptor acceptor;
	private int port = 7654;
	
	public void start(){
		acceptor = new NioSocketAcceptor();
		//设定 对象传输工厂
		ObjectSerializationCodecFactory factory = new ObjectSerializationCodecFactory();
		factory.setDecoderMaxObjectSize(Integer.MAX_VALUE);
		factory.setEncoderMaxObjectSize(Integer.MAX_VALUE);
		//指定编码过滤器
		acceptor.getFilterChain().addLast("object", new ProtocolCodecFilter(factory));
		//指定业务逻辑类
		acceptor.setHandler(new FileServerHandler());
		//设置监听地址和端口
		acceptor.setDefaultLocalAddress(new InetSocketAddress("127.0.0.1",port));
		try {
			//启动监听
			acceptor.bind();
			logger.info("Socket服务器启动完成,监听于端口:"+port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void destroy() throws Exception {
        acceptor.unbind();
        acceptor.dispose();
    }
	
}
