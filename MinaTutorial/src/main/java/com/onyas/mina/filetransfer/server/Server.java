package com.onyas.mina.filetransfer.server;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.onyas.mina.filetransfer.util.MathProtocolCodecFactory;

public class Server {
	public void bindServer()  throws Exception{
		IoAcceptor acceptor=new NioSocketAcceptor(); 
		acceptor.getSessionConfig().setReadBufferSize(1024*2); 
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,10); 
		acceptor.getFilterChain().addLast("codec",
		new ProtocolCodecFilter(new MathProtocolCodecFactory(true))); 
		acceptor.setHandler(new ServerHandler());
		acceptor.bind(new InetSocketAddress(9123)); 
	}
	public static void main(String[] args) {
		Server server = new Server();
		try {
			server.bindServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
