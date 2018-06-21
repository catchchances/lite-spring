package com.kkdz.code.beans.factory.config;

public interface SingletonBeanRegister {

	Object getSingleton(String beanName);

	void registerSingleton(String beanName, Object singleton);

}
