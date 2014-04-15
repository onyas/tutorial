package com.test.spring4;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tutorial.spring4.HelloWorld;

public class TestHelloWorld {

	@Test
	public void testSayHello() {
		HelloWorld hw = new HelloWorld();
		hw.setName("Test");
		hw.sayHello();
	}

	@Test
	public void testSayHelloWithSping1() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		HelloWorld hw = (HelloWorld) context.getBean("helloworld");
		hw.sayHello();
	}
	
	@Test
	public void testSayHelloWithSping2() {
		//���������ӡ�Ľ�����Է��֣������������ʱ�򣬾��Ѿ�ʵ�������󣬲���Ϊ���Ը�ֵ
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
	}
}
