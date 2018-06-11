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
	private Map<String, Object> beansMap = new HashMap<>();

	public DefaultBeanFactory(String configFile) {
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
				String clsId = e.attributeValue(CLASS_ID);
				String clsName = e.attributeValue(CLASS_NAME);
				Object bean = loadBean(clsName);
				beansMap.put(clsId, bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Object loadBean(String clsName) {
		try {
			Class<?> bean = ClassLoader.getSystemClassLoader().loadClass(clsName);
			return bean.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BeanDefinition getBeanDefinition(String beanId) {
		return new GenericBeanDefinition(beanId, beansMap.get(beanId).getClass().getName());
	}

	@Override
	public Object getBean(String beanId) {
		return beansMap.get(beanId);
	}

}
