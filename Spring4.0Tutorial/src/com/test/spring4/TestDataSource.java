package com.test.spring4;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tutorial.spring4.DataSource;

public class TestDataSource {

	@Test
	public void test() throws SQLException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		
		DataSource ds = (DataSource) ctx.getBean("dataSource");
		System.out.println(ds);
		
		javax.sql.DataSource dataSource = (javax.sql.DataSource) ctx.getBean("dataSource2");
		System.out.println(dataSource.getConnection());
	}

}
