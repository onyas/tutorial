package com.onyas.mina.filetransferused.client;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class FileClient {

	private static Logger logger = Logger.getLogger(FileClient.class);

	private static NioSocketConnector connector = new NioSocketConnector();
	
	static{
		ObjectSerializationCodecFactory factory = new ObjectSerializationCodecFactory();
		factory.setDecoderMaxObjectSize(Integer.MAX_VALUE);
		factory.setEncoderMaxObjectSize(Integer.MAX_VALUE);
		connector.getFilterChain().addLast("object",new ProtocolCodecFilter(factory));
		// 设置事件处理器
		connector.setHandler(new FileClientHandler());
	}
	
	public static IoSession createSesion(String hostname,int port){
		IoSession session=null;
		// 建立连接
		ConnectFuture cf = connector.connect(new InetSocketAddress(hostname, port));
		// 等待连接创建完成
		cf.awaitUninterruptibly();
		if(cf.isConnected()){
			session = cf.getSession();
			logger.info("与["+hostname+":"+port+"]连接已建立");
		}
		return session;
	}
	
}
