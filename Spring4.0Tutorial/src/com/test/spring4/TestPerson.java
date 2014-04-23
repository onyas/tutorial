package com.test.spring4;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tutorial.spring4.Person;

public class TestPerson {

	@Test
	public void test() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		Person person = (Person) ctx.getBean("person");
		System.out.println(person);

	}
	
}
