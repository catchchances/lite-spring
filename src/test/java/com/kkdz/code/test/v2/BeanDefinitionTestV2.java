package com.kkdz.code.test.v2;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.kkdz.code.beans.BeanDefinition;
import com.kkdz.code.beans.PropertyValue;
import com.kkdz.code.beans.RuntimeBeanReference;
import com.kkdz.code.beans.factory.support.DefaultBeanFactory;
import com.kkdz.code.beans.factory.xml.XmlBeanDefinitionReader;
import com.kkdz.code.core.io.ClassPathResource;

public class BeanDefinitionTestV2 {

	@Test
	public void testGetBeanDefinition() {
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));

		BeanDefinition bd = factory.getBeanDefinition("petStore");

		List<PropertyValue> pvs = bd.getPropertyValues();

		// Assert.assertTrue(pvs.size() == 3);
		{
			PropertyValue pv = this.getPropertyValue("accountDao", pvs);
			assertNotNull(pv);
			assertTrue(pv.getValue() instanceof RuntimeBeanReference);
		}

		{
			PropertyValue pv = this.getPropertyValue("itemDao", pvs);
			assertNotNull(pv);
			assertTrue(pv.getValue() instanceof RuntimeBeanReference);
		}

	}

	private PropertyValue getPropertyValue(String name, List<PropertyValue> pvs) {
		for (PropertyValue pv : pvs) {
			if (pv.getName().equals(name)) {
				return pv;
			}
		}
		return null;
	}

}
