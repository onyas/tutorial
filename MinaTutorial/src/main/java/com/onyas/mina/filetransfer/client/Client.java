package com.onyas.mina.filetransfer.client;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.onyas.mina.filetransfer.util.MathProtocolCodecFactory;

public class Client {
	
	public IoSession creatClient(){
		IoConnector connector=new NioSocketConnector(); 
		connector.setConnectTimeoutMillis(30000); 
		connector.getFilterChain().addLast("codec", 
		new ProtocolCodecFilter(new MathProtocolCodecFactory(false)));
		connector.setHandler(new ClientHandler());
		ConnectFuture future = connector.connect(new InetSocketAddress("127.0.0.1", 9123)); 
		// 等待是否连接成功，相当于是转异步执行为同步执行。 
				future.awaitUninterruptibly(); 
				// 连接成功后获取会话对象。 如果没有上面的等待， 由于connect()方法是异步的， session可能会无法获取。 
		IoSession session = null;
		try{
			session = future.getSession();
		}catch(Exception e){
			e.printStackTrace();
		}
		return session;
	}
	public static void main(String[] args) {
		Client client = new Client();
		client.creatClient();
	}
}
