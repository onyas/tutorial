package com.test.spring4;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tutorial.spring4.CarStore2;

public class TestCarStore2 {

	@Test
	public void test() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		
		CarStore2 cs2 = (CarStore2) ctx.getBean("carStore2");
		System.out.println(cs2);
	}

}
