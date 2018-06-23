package com.kkdz.code.test.v2;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.kkdz.code.context.ApplicationContext;
import com.kkdz.code.context.support.ClassPathXmlApplication;
import com.kkdz.code.service.v1.PetStoreService;
import com.kkdz.code.service.v2.AccountDao;
import com.kkdz.code.service.v2.ItemDao;

public class ApplicationContextTestV2 {

	@Test
	public void testGetBeanProperty() {
		ApplicationContext ctx = new ClassPathXmlApplication("petstore-v2.xml");
		PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");

		assertNotNull(petStore.getAccountDao());
		assertNotNull(petStore.getItemDao());

		assertTrue(petStore.getAccountDao() instanceof AccountDao);
		assertTrue(petStore.getItemDao() instanceof ItemDao);
		assertTrue(petStore.getEnv().equals("test"));
		assertTrue(petStore.getVersion() == 2);
	}

}
