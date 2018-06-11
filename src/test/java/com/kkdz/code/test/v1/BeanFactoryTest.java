package com.kkdz.code.test.v1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.kkdz.code.beans.BeanDefinition;
import com.kkdz.code.beans.factory.BeanFactory;
import com.kkdz.code.beans.factory.support.DefaultBeanFactory;
import com.kkdz.code.service.v1.PetStoreService;

public class BeanFactoryTest {

	@Test
	public void testGetBean() {
		BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
		BeanDefinition bd = factory.getBeanDefinition("petStore");

		String beanClassName = bd.getBeanClassName();

		assertEquals("com.kkdz.code.service.v1.PetStoreService", beanClassName);
		PetStoreService petStore = (PetStoreService) factory.getBean("petStore");
		assertNotNull(petStore);
	}

}
