package com.tutorial.spring4;

public class HelloWorld {

	private String name;
	
	
	public HelloWorld() {
		System.out.println("HelloWorld.java  Constuctor...");
	}
	
	public void setName(String name) {
		this.name = name;
		System.out.println("HelloWorld.java  Name:"+name);
	}
	
	public void sayHello(){
		System.out.println("Hello "+name);
	}
	
}
