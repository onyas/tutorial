package com.tutorial.spring4;

public class HelloWorld {

	private String name;
	
	
	public HelloWorld() {
		System.out.println("Constuctor...");
	}
	
	public void setName(String name) {
		this.name = name;
		System.out.println("Name:"+name);
	}
	
	public void sayHello(){
		System.out.println("Hello "+name);
	}
	
}
