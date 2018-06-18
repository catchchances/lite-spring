package com.kkdz.code.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.kkdz.code.beans.BeanDefinition;
import com.kkdz.code.beans.factory.BeanDefinitionStoreException;
import com.kkdz.code.beans.factory.support.BeanDefinitionRegister;
import com.kkdz.code.beans.factory.support.GenericBeanDefinition;
import com.kkdz.code.core.Resource;

public class XmlBeanDefinitionReader {

	private static final String CLASS_NAME = "class";
	private static final String CLASS_ID = "id";
	private static final String SCOPE_ATTRIBUTE = "scope";

	BeanDefinitionRegister register;

	public XmlBeanDefinitionReader(BeanDefinitionRegister register) {
		this.register = register;
	}

	public void loadBeanDefinitions(Resource resource) {
		InputStream is = null;
		try {
			is = resource.getInputStream();
			SAXReader reader = new SAXReader();
			Document document = reader.read(is);
			Element root = document.getRootElement();
			@SuppressWarnings("unchecked")
			Iterator<Element> iterator = root.elementIterator();
			while (iterator.hasNext()) {
				Element e = (Element) iterator.next();
				String beanId = e.attributeValue(CLASS_ID);
				String beanClassName = e.attributeValue(CLASS_NAME);
				BeanDefinition bd = new GenericBeanDefinition(beanId, beanClassName);
				String scope = e.attributeValue(SCOPE_ATTRIBUTE);
				if (scope != null) {
					bd.setScope(scope);
				}
				register.registerBeanDefinition(beanId, bd);
			}
		} catch (Exception e) {
			throw new BeanDefinitionStoreException(e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
