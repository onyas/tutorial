package com.onyas.thrift.demo;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

public class Server {
	private void start() {
		try {
			TServerSocket serverTransport = new TServerSocket(7911);
			Something.Processor processor = new Something.Processor(
					new SomethingImpl());
			
			
			Factory protFactory = new TBinaryProtocol.Factory(true, true);
			Args args = new Args(serverTransport);
			TServer server = new TThreadPoolServer(args);
			System.out.println("Starting server on port 7911 ...");
			server.serve();

		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Server srv = new Server();
		srv.start();
	}
}