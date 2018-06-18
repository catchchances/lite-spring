package com.kkdz.code.test.v1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.kkdz.code.beans.BeanDefinition;
import com.kkdz.code.beans.factory.BeanCreationException;
import com.kkdz.code.beans.factory.BeanDefinitionStoreException;
import com.kkdz.code.beans.factory.support.DefaultBeanFactory;
import com.kkdz.code.beans.factory.xml.XmlBeanDefinitionReader;
import com.kkdz.code.core.io.ClassPathResource;
import com.kkdz.code.service.v1.PetStoreService;

public class BeanFactoryTest {

	private DefaultBeanFactory factory = null;

	private XmlBeanDefinitionReader reader = null;

	@Before
	public void setUp() {
		this.factory = new DefaultBeanFactory();
		this.reader = new XmlBeanDefinitionReader(factory);
	}

	@Test
	public void testGetBean() {

		reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));

		BeanDefinition bd = factory.getBeanDefinition("petStore");

		assertTrue(bd.isSingleton());

		assertFalse(bd.isPrototype());

		assertEquals(BeanDefinition.SCOPE_DEFAULT, bd.getScope());

		String beanClassName = bd.getBeanClassName();

		assertEquals("com.kkdz.code.service.v1.PetStoreService", beanClassName);

		PetStoreService petStore = (PetStoreService) factory.getBean("petStore");

		assertNotNull(petStore);
	}

	@Test
	public void testInvalidBean() {
		reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));
		try {
			factory.getBean("invalidBean");
		} catch (BeanCreationException e) {
			return;
		}
		Assert.fail("export BeanCreationException");
	}

	@Test
	public void testInvalidXml() {
		try {
			reader.loadBeanDefinitions(new ClassPathResource("xxx.xml"));
		} catch (BeanDefinitionStoreException e) {
			return;
		}
		Assert.fail("store BeanDefinition Exception");
	}

}
