package com.kkdz.code.beans.factory.support;

import java.util.HashMap;
import java.util.Map;

import com.kkdz.code.beans.BeanDefinition;
import com.kkdz.code.beans.factory.BeanCreationException;
import com.kkdz.code.beans.factory.config.ConfigurableBeanFactory;

public class DefaultBeanFactory implements ConfigurableBeanFactory, BeanDefinitionRegister {

	private ClassLoader beanClassLoader;

	private Map<String, BeanDefinition> definitionBeanMap = new HashMap<>();

	public DefaultBeanFactory() {
	}

	/**
	 * getBean是get实例
	 */
	@Override
	public Object getBean(String beanId) {

		BeanDefinition beanDefinition = this.definitionBeanMap.get(beanId);
		if (beanDefinition == null) {
			return null;
		}
		try {
			Class<?> bean = getClassLoader().loadClass(beanDefinition.getBeanClassName());
			return bean.newInstance();
		} catch (Exception e) {
			throw new BeanCreationException(beanId, "", e);
		}
	}

	/**
	 * getBeanDefinition是get bean的定义
	 */
	@Override
	public BeanDefinition getBeanDefinition(String beanId) {
		return definitionBeanMap.get(beanId);
	}

	@Override
	public void registerBeanDefinition(String beanId, BeanDefinition bd) {
		this.definitionBeanMap.put(beanId, bd);
	}

	@Override
	public void setBeanClassLoader(ClassLoader beanClassLoader) {
		this.beanClassLoader = beanClassLoader;

	}

	@Override
	public ClassLoader getClassLoader() {
		return this.beanClassLoader != null ? this.beanClassLoader : ClassLoader.getSystemClassLoader();

	}

}
