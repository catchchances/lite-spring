package com.kkdz.code.context;

import com.kkdz.code.beans.factory.support.DefaultBeanFactory;
import com.kkdz.code.beans.factory.xml.XmlBeanDefinitionReader;
import com.kkdz.code.core.Resource;

public abstract class AbstractApplicationContext implements ApplicationContext {

	private DefaultBeanFactory factory;

	private ClassLoader beanClassLoader;

	public AbstractApplicationContext(String configFile) {
		this.factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		Resource resource = getResourceByPath(configFile);
		reader.loadBeanDefinitions(resource);
		factory.setBeanClassLoader(this.getClassLoader());
	}

//	@Override
//	public BeanDefinition getBeanDefinition(String beanId) {
//		return this.factory.getBeanDefinition(beanId);
//	}

	@Override
	public Object getBean(String beanId) {
		return this.factory.getBean(beanId);
	}

	protected abstract Resource getResourceByPath(String path);

	@Override
	public void setBeanClassLoader(ClassLoader beanClassLoader) {
		this.beanClassLoader = beanClassLoader;

	}

	@Override
	public ClassLoader getClassLoader() {
		return this.beanClassLoader != null ? this.beanClassLoader : ClassLoader.getSystemClassLoader();

	}

}
