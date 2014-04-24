package com.tutorial.spring4;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {

	/**
	 * bean ��Beanʵ������ beanName:IOC�������õ�bean����
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		System.out.println("postProcessAfterInitialization" + bean + ","
				+ beanName);
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		System.out.println("postProcessAfterInitialization" + bean + ","
				+ beanName);
		return bean;
	}

}
