package com.kkdz.code.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.kkdz.code.beans.BeanDefinition;
import com.kkdz.code.beans.ConstructorArgument;
import com.kkdz.code.beans.PropertyValue;
import com.kkdz.code.beans.factory.BeanDefinitionStoreException;
import com.kkdz.code.beans.factory.config.RuntimeBeanReference;
import com.kkdz.code.beans.factory.config.TypedStringValue;
import com.kkdz.code.beans.factory.support.BeanDefinitionRegister;
import com.kkdz.code.beans.factory.support.GenericBeanDefinition;
import com.kkdz.code.core.Resource;
import com.kkdz.code.utils.StringUtils;

public class XmlBeanDefinitionReader {

	protected final Log logger = LogFactory.getLog(getClass());

	private static final String CLASS_NAME = "class";
	private static final String CLASS_ID = "id";
	private static final String SCOPE_ATTRIBUTE = "scope";
	private static final String PROPERTY_ELEMENT = "property";
	private static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";
	private static final String REF_ATTRIBUTE = "ref";
	private static final String VALUE_ATTRIBUTE = "value";
	private static final String NAME_ATTRIBUTE = "name";
	private static final String TYPE_ATTRIBUTE = "type";

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
				Element ele = (Element) iterator.next();
				String beanId = ele.attributeValue(CLASS_ID);
				String beanClassName = ele.attributeValue(CLASS_NAME);
				BeanDefinition bd = new GenericBeanDefinition(beanId, beanClassName);
				String scope = ele.attributeValue(SCOPE_ATTRIBUTE);
				if (scope != null) {
					bd.setScope(scope);
				}
				parsePropertyElement(ele, bd);
				parseConstructorArgElements(ele, bd);
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

	private void parseConstructorArgElements(Element ele, BeanDefinition bd) {
		@SuppressWarnings("unchecked")
		Iterator<Element> iter = ele.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
		while (iter.hasNext()) {
			Element argElem = iter.next();
			parseArgumentValue(argElem, bd);
		}

	}

	private void parseArgumentValue(Element argElem, BeanDefinition bd) {
		String name = argElem.attributeValue(NAME_ATTRIBUTE);
		String type = argElem.attributeValue(TYPE_ATTRIBUTE);
		Object value = parsePropertyValue(argElem, bd, null);// null表示是的构造器
		ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(value);
		if (StringUtils.hasLength(name)) {
			valueHolder.setName(name);
		}
		if (StringUtils.hasLength(type)) {
			valueHolder.setType(type);
		}
		bd.getConstructorArgument().addArgumentValue(valueHolder);
	}

	private void parsePropertyElement(Element beanElem, BeanDefinition bd) {
		@SuppressWarnings("unchecked")
		Iterator<Element> iter = beanElem.elementIterator(PROPERTY_ELEMENT);
		while (iter.hasNext()) {
			Element propElem = iter.next();
			String propertyName = propElem.attributeValue(NAME_ATTRIBUTE);
			if (!StringUtils.hasLength(propertyName)) {
				logger.fatal("Tag 'property' must have a 'name' attribute");
				return;
			}
			Object val = parsePropertyValue(propElem, bd, propertyName);
			PropertyValue pv = new PropertyValue(propertyName, val);
			bd.getPropertyValues().add(pv);
		}
	}

	private Object parsePropertyValue(Element ele, BeanDefinition bd, String propertyName) {
		String elementName = propertyName != null ? "<property> element for property '" + propertyName + "'"
				: "<constructor-arg> element";

		boolean hasRefAttribute = ele.attribute(REF_ATTRIBUTE) != null;
		boolean hasValAttribute = ele.attribute(VALUE_ATTRIBUTE) != null;

		if (hasRefAttribute) {
			String refName = ele.attributeValue(REF_ATTRIBUTE);
			if (!StringUtils.hasText(refName)) {
				logger.error(elementName + " contains empty 'ref' attribute");
			}
			RuntimeBeanReference ref = new RuntimeBeanReference(refName);
			return ref;
		} else if (hasValAttribute) {
			TypedStringValue valueHolder = new TypedStringValue(ele.attributeValue(VALUE_ATTRIBUTE));
			return valueHolder;
		} else {
			throw new RuntimeException(elementName + " must specify a ref or name");
		}

	}

}
