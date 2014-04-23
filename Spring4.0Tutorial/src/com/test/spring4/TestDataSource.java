package com.test.spring4;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tutorial.spring4.DataSource;

public class TestDataSource {

	@Test
	public void test() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		
		DataSource ds = (DataSource) ctx.getBean("dataSource");
		System.out.println(ds);
	}

}
