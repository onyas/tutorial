package com.onyas.hadoop.tutorial;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.net.URL;

import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

public class HDFSUrlTest {

	//让Java程序识别HDFS的URL
	static{
		URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
	}
	
	@Test
	public void testRead() throws Exception {
		InputStream is = null;
		String filePath = "hdfs://hadoop-master:9000/wc/test/touch.txt";
		//获取文件输入流
		is = new URL(filePath).openStream();
		//读取文件到控制台
		IOUtils.copyBytes(is, System.out, 4096, false);
		//关闭输入流
		IOUtils.closeStream(is);
	}
	
	
	
}
