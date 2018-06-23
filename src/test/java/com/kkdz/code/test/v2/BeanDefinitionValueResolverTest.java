package com.kkdz.code.test.v2;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.kkdz.code.beans.BeanDefinitionValueResolver;
import com.kkdz.code.beans.RuntimeBeanReference;
import com.kkdz.code.beans.TypedStringValue;
import com.kkdz.code.beans.factory.support.DefaultBeanFactory;
import com.kkdz.code.beans.factory.xml.XmlBeanDefinitionReader;
import com.kkdz.code.core.io.ClassPathResource;
import com.kkdz.code.service.v2.AccountDao;

public class BeanDefinitionValueResolverTest {

	@Test
	public void testResolveRuntimBeanReference() {
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));

		BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);
		RuntimeBeanReference reference = new RuntimeBeanReference("accountDao");
		Object value = resolver.resolveValueIfNeccessary(reference);

		assertNotNull(value);
		assertTrue(value instanceof AccountDao);

	}

	@Test
	public void testTypedStringValue() {
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));

		BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);
		TypedStringValue stringValue = new TypedStringValue("test");
		Object value = resolver.resolveValueIfNeccessary(stringValue);

		assertNotNull(value);
		assertTrue(value.equals("test"));

	}

}
