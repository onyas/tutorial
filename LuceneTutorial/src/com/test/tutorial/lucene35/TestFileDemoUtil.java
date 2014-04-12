package com.test.tutorial.lucene35;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import com.tutorial.lucene35.FileDemoUtil;

public class TestFileDemoUtil {

	@Test
	public void testCopyFile() {
		File dir = new File("F:/Test/lucene/txt");
		for (File f : dir.listFiles()) {
			try {
				FileUtils.copyFile(f, new File(f.getParent()
						+ File.separatorChar
						+ FilenameUtils.getBaseName(f.getAbsolutePath())+".adad"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testIndexfile(){
		FileDemoUtil.indexFiles();
	}

}
