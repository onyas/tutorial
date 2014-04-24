package com.test.spring4;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tutorial.spring4.LifeCycle;

public class TestLifeCycle {

	@Test
	public void test() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		LifeCycle lc = (LifeCycle) context.getBean("lifeCycle");
		System.out.println(lc);
		context.close();
	}

}
