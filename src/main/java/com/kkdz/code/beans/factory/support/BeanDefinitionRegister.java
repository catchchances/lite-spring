package com.kkdz.code.beans.factory.support;

import com.kkdz.code.beans.BeanDefinition;

public interface BeanDefinitionRegister {

	BeanDefinition getBeanDefinition(String beanId);

	void registerBeanDefinition(String beanId, BeanDefinition bd);

}
