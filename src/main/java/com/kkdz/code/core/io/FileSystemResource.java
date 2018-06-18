package com.kkdz.code.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.kkdz.code.core.Resource;
import com.kkdz.code.utils.Assert;

public class FileSystemResource implements Resource {

	private String path;
	private File file;

	public FileSystemResource(String path) {
		Assert.notNull(path, "Path cannot be null");
		this.file = new File(path);
		this.path = path;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(this.file);
	}

	@Override
	public String getDescription() {
		return "file:" + this.file.getAbsolutePath();
	}

}
