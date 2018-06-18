package com.kkdz.code.context.support;

import com.kkdz.code.context.AbstractApplicationContext;
import com.kkdz.code.core.Resource;
import com.kkdz.code.core.io.ClassPathResource;

public class ClassPathXmlApplication extends AbstractApplicationContext {

	public ClassPathXmlApplication(String configFile) {
		super(configFile);
	}

	@Override
	protected Resource getResourceByPath(String path) {
		return new ClassPathResource(path, this.getClassLoader());
	}

}
