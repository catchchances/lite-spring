package com.kkdz.code.beans;

public class RuntimeBeanReference {

	private final String beanName;

	public RuntimeBeanReference(String beanName) {
		this.beanName = beanName;
	}

	public String getBeanName() {
		return this.beanName;
	}

}
