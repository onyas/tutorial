package com.onyas.thrift.demo;

import org.apache.thrift.TException;

class SomethingImpl implements Something.Iface {
	public SomethingImpl() {
	}

	public int ping() throws TException {
		System.out.println("Recieve ping from client...");
		return 0;
	}
}