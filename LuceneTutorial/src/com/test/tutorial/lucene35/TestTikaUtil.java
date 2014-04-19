package com.test.tutorial.lucene35;

import java.io.File;

import org.junit.Test;

import com.tutorial.lucene35.TikaUtil;

public class TestTikaUtil {

	@Test
	public void index() {
		TikaUtil tu = new TikaUtil();
		tu.index();
	}

	@Test
	public void testFile2Text() {
		TikaUtil tu = new TikaUtil();
		System.out.println(tu.file2Text(new File(
				"F:\\Test\\lucene\\tikaexample\\招聘试题.doc")));
	}

	@Test
	public void testEasyway() {
		TikaUtil tu = new TikaUtil();
		System.out.println(tu.file2Text(new File(
				"F:\\Test\\lucene\\tikaexample\\招聘试题.doc")));
	}

}
