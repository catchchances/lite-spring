package com.kkdz.code.beans.factory.support;

import com.kkdz.code.beans.BeanDefinition;

public class GenericBeanDefinition implements BeanDefinition {

	private String beanId;

	private String beanClassName;

	public GenericBeanDefinition(String beanId, String beanClassName) {
		this.beanId = beanId;
		this.beanClassName = beanClassName;
	}

	@Override
	public String getBeanClassName() {
		return this.beanClassName;
	}

	public String getBeanId() {
		return this.beanId;
	}

}
