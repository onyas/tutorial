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
		 * ��SpringIOC������ȡBean���ô���Beanʵ��֮ǰ�������������ʵ������
		 * ֻ��������ʵ�����󣬲ſ��Դ�IOC�������ȡBean��ʵ����ʹ��
		 */
		//1������Spring��IOC��������
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		//2����IOC�����л�ȡBeanʵ��
		HelloWorld hw = (HelloWorld) context.getBean("helloworld");
		//3������sayHello����
		hw.sayHello();
	}
	
	@Test
	public void testSayHelloWithSping2() {
		//���������ӡ�Ľ�����Է��֣������������ʱ�򣬾��Ѿ�ʵ�������󣬲���Ϊ���Ը�ֵ
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
	}
}
