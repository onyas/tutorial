package com.tutorial.spring4;


public class LifeCycle {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		System.out.println("LifeCycle setName...");
		this.name = name;
	}
	
	public LifeCycle() {
		System.out.println("LifeCycle Constructor..");
	}

	@Override
	public String toString() {
		return "LifeCycle [name=" + name + "]";
	}
	
	public void init(){
		System.out.println("Init...");
	}
	
	public void destroy(){
		System.out.println("destroy");
	}
	
}
