package com.kkdz.code.beans.factory.support;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.kkdz.code.beans.BeanDefinition;
import com.kkdz.code.beans.factory.BeanFactory;

public class DefaultBeanFactory implements BeanFactory {

	private static final String CLASS_NAME = "class";
	private static final String CLASS_ID = "id";
	private Map<String, BeanDefinition> definitionBeanMap = new HashMap<>();

	public DefaultBeanFactory(String configFile) {
		loadBeanDefinition(configFile);
	}

	private void loadBeanDefinition(String configFile) {
		URL url = ClassLoader.getSystemClassLoader().getResource(configFile);
		File file = new File(url.getPath());
		try {
			String xmlStr = FileUtils.readFileToString(file);
			Document document = DocumentHelper.parseText(xmlStr);
			Element root = document.getRootElement();
			@SuppressWarnings("unchecked")
			Iterator<Element> iterator = root.elementIterator();
			while (iterator.hasNext()) {
				Element e = (Element) iterator.next();
				String beanId = e.attributeValue(CLASS_ID);
				String beanClassName = e.attributeValue(CLASS_NAME);
				BeanDefinition bean = new GenericBeanDefinition(beanId, beanClassName);
				definitionBeanMap.put(beanId, bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// private BeanDefinition loadBean(String clsName) {
	// try {
	// Class<?> bean = ClassLoader.getSystemClassLoader().loadClass(clsName);
	// return (BeanDefinition) bean.newInstance();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }

	/**
	 * getBeanDefinition是get bean的定义
	 */
	@Override
	public BeanDefinition getBeanDefinition(String beanId) {
		return definitionBeanMap.get(beanId);
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
			Class<?> bean = ClassLoader.getSystemClassLoader().loadClass(beanDefinition.getBeanClassName());
			return bean.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
