package com.kkdz.code.test.v1;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.kkdz.code.context.ApplicationContext;
import com.kkdz.code.context.support.ClassPathXmlApplication;
import com.kkdz.code.context.support.FileSystemXmlApplicationContext;
import com.kkdz.code.service.v1.PetStoreService;

public class ApplicationContextTest {

	@Test
	public void testGetBean() {
		ApplicationContext ctx = new ClassPathXmlApplication("petstore-v1.xml");
		PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");
		assertNotNull(petStore);
	}

	@Test
	public void testGetBeanFromFileSystemXmlApplicationContext() {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("d:\\petstore-v1.xml");
		PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");
		assertNotNull(petStore);
	}

}
