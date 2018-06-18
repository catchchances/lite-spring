package com.kkdz.code.context.support;

import com.kkdz.code.context.AbstractApplicationContext;
import com.kkdz.code.core.Resource;
import com.kkdz.code.core.io.FileSystemResource;

public class FileSystemXmlApplicationContext extends AbstractApplicationContext {

	public FileSystemXmlApplicationContext(String configFile) {
		super(configFile);
	}

	@Override
	protected Resource getResourceByPath(String path) {
		return new FileSystemResource(path);
	}

}
