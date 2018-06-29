package com.kkdz.code.beans.factory.support;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kkdz.code.beans.BeanDefinition;
import com.kkdz.code.beans.PropertyValue;
import com.kkdz.code.beans.SimpleTypeConverter;
import com.kkdz.code.beans.TypeConverter;
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
		Object bean = instantiateBean(bd);
		populateBean(bd, bean);
		return bean;
	}

	private void populateBean(BeanDefinition bd, Object bean) {
		List<PropertyValue> pvs = bd.getPropertyValues();
		if (pvs == null || pvs.size() == 0) {
			return;
		}
		BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
		TypeConverter converter = new SimpleTypeConverter();
		try {
			for (PropertyValue pv : pvs) {
				String propertyName = pv.getName();
				Object originalValue = pv.getValue();
				Object resolvedValue = resolver.resolveValueIfNeccessary(originalValue);
				BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
				PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
				for (PropertyDescriptor pd : pds) {
					if (pd.getName().equals(propertyName)) {
						Object convertedValue = converter.convertIfNecessary(resolvedValue, pd.getPropertyType());
						pd.getWriteMethod().invoke(bean, convertedValue);
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new BeanCreationException("Failed to obtain BeanInfo for class [" + bd.getBeanClassName() + "].");
		}

	}

	private Object instantiateBean(BeanDefinition bd) {
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
