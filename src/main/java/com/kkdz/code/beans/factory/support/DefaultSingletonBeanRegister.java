package com.kkdz.code.beans.factory.support;

import java.util.HashMap;
import java.util.Map;

import com.kkdz.code.beans.factory.config.SingletonBeanRegister;
import com.kkdz.code.utils.Assert;

public class DefaultSingletonBeanRegister implements SingletonBeanRegister {

	private final Map<String, Object> singletonObjects = new HashMap<>();

	@Override
	public void registerSingleton(String beanName, Object singletonObject) {
		Assert.notNull(beanName, "'beanName' must not be null");
		Object oldObject = this.singletonObjects.get(beanName);
		if (oldObject != null) {
			throw new IllegalStateException("Could not register object [" + singletonObject + "] under bean name '"
					+ beanName + "': there is already object [" + oldObject + "' exist.");
		}
		this.singletonObjects.put(beanName, singletonObject);
	}

	@Override
	public Object getSingleton(String beanName) {
		return this.singletonObjects.get(beanName);
	}

}
