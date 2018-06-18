package com.kkdz.code.core.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.kkdz.code.core.Resource;

public class ClassPathResource implements Resource {

	private String path;
	private ClassLoader classLoader;

	public ClassPathResource(String path) {
		this(path, (ClassLoader) null);
	}

	public ClassPathResource(String path, ClassLoader classLoader) {
		this.path = path;
		this.classLoader = classLoader != null ? this.classLoader = classLoader : ClassLoader.getSystemClassLoader();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		InputStream is = this.classLoader.getResourceAsStream(this.path);
		if (is == null) {
			throw new FileNotFoundException(path + "cannot be opened");
		}
		return is;
	}

	@Override
	public String getDescription() {
		return "file:"+this.classLoader;
	}

}
