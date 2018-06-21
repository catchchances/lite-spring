package com.kkdz.code.beans.factory.support;

import java.util.HashMap;
import java.util.Map;

import com.kkdz.code.beans.BeanDefinition;
import com.kkdz.code.beans.factory.BeanCreationException;
import com.kkdz.code.beans.factory.config.ConfigurableBeanFactory;

public class DefaultBeanFactory extends DefaultSingletonBeanRegister
		implements ConfigurableBeanFactory, BeanDefinitionRegister {

	private ClassLoader beanClassLoader;

	private Map<String, BeanDefinition> definitionBeanMap = new HashMap<>();

	public DefaultBeanFactory() {
	}

	/**
	 * getBean是get实例
	 */
	@Override
	public Object getBean(String beanId) {

		BeanDefinition bd = this.definitionBeanMap.get(beanId);
		if (bd == null) {
			return null;
		}
		if (bd.isSingleton()) {
			Object bean = this.getSingleton(beanId);
			if (bean == null) {
				bean = create(bd);
				registerSingleton(beanId, bean);
			}
			return bean;
		}
		return create(bd);

	}

	private Object create(BeanDefinition bd) {
		ClassLoader cl = this.getClassLoader();
		String beanClassName = bd.getBeanClassName();
		try {
			Class<?> clz = cl.loadClass(beanClassName);
			return clz.newInstance();
		} catch (Exception e) {
			throw new BeanCreationException("create bean for '" + beanClassName + "' failed", e);
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
