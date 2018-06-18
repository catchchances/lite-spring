package com.kkdz.code.test.v1;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.kkdz.code.core.Resource;
import com.kkdz.code.core.io.ClassPathResource;
import com.kkdz.code.core.io.FileSystemResource;

public class ResourceTest {

	@Test
	public void testClassPathResource() throws IOException {
		Resource r = new ClassPathResource("petstore-v1.xml");
		InputStream is = null;
		try {
			is = r.getInputStream();
			// todo
			assertNotNull(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	@Test
	public void testFileSystemResource() throws IOException {
		Resource r = new FileSystemResource("d:\\petstore-v1.xml");
		InputStream is = null;
		try {
			is = r.getInputStream();
			// todo
			assertNotNull(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

}
