package com.kkdz.code.beans.factory.config;

import com.kkdz.code.beans.factory.BeanFactory;

public interface ConfigurableBeanFactory extends BeanFactory {

	void setBeanClassLoader(ClassLoader classLoader);

	ClassLoader getClassLoader();

}
