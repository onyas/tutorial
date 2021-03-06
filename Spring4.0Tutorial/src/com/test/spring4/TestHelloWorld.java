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
		/**
		 * 在SpringIOC容器读取Bean配置创建Bean实例之前，必须对它进行实例化，
		 * 只有在容器实例化后，才可以从IOC容器里获取Bean的实例并使用
		 */
		//1、创建Spring的IOC容器对象
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		//2、从IOC容器中获取Bean实例
		HelloWorld hw = (HelloWorld) context.getBean("helloworld");
		//3、调用sayHello方法
		hw.sayHello();
	}
	
	@Test
	public void testSayHelloWithSping2() {
		//根据这个打印的结果可以发现，在容器构造的时候，就已经实例化对象，并且为属性赋值
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
	}
}
