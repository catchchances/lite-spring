package com.kkdz.code.beans.factory.support;

import com.kkdz.code.beans.factory.config.RuntimeBeanReference;
import com.kkdz.code.beans.factory.config.TypedStringValue;

public class BeanDefinitionValueResolver {

	private DefaultBeanFactory factory;

	public BeanDefinitionValueResolver(DefaultBeanFactory factory) {
		this.factory = factory;
	}

	public Object resolveValueIfNeccessary(Object value) {
		if (value instanceof RuntimeBeanReference) {
			RuntimeBeanReference ref = (RuntimeBeanReference) value;
			Object bean = this.factory.getBean(ref.getBeanName());
			return bean;
		} else if (value instanceof TypedStringValue) {
			TypedStringValue typedStringHolder = (TypedStringValue) value;
			return typedStringHolder.getValue();
		} else {
			throw new RuntimeException("the value " + value + " has not implemented");
		}
	}

}
