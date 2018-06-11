package com.kkdz.code.beans.factory;

import com.kkdz.code.beans.BeanDefinition;

public interface BeanFactory {

	public BeanDefinition getBeanDefinition(String string);

	public Object getBean(String beanId);

}
