package com.onyas.hadoop.tutorial;


import static org.junit.Assert.*;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import com.onyas.hadoop.tutorial.util.HDFSUtil;

public class HDFSTest {

	/**
	 * 查看文件目录
	 * @throws Exception
	 */
	@Test
	public void testList() throws Exception {
		//获取文件系统
		FileSystem hdfs = HDFSUtil.getFileSystem();
		//指定目录
		Path path = new Path("/wc/test");
		FileStatus[] fileStatus = hdfs.listStatus(path);
		for(FileStatus fs :fileStatus){
			Path p = fs.getPath();
			String info = fs.isDir()?"目录":"文件";
			System.out.println(info+":"+p);
		}
	}
	
	/**
	 * 读取文件内容
	 * @throws Exception
	 */
	@Test
	public void testReadFile() throws Exception {
		//获取文件系统
		FileSystem hdfs = HDFSUtil.getFileSystem();
		//指定文件
		Path path = new Path("/wc/test/touch.txt");
		//打开文件输入流
		FSDataInputStream inputStream = hdfs.open(path);
		//读文件内容到控制台显示
		IOUtils.copyBytes(inputStream, System.out, 4096,false);
		//关闭输入流
		IOUtils.closeStream(inputStream);
	}
	
	/**
	 * 创建目录
	 * @throws Exception
	 */
	@Test
	public void testMakeDir() throws Exception {
		//获取文件系统
		FileSystem hdfs = HDFSUtil.getFileSystem();
		//指定文件
		Path path = new Path("/wc/test");
		boolean isSuccess = hdfs.mkdirs(path);
		System.out.println("创建目录【"+path+"】"+isSuccess);
	}

	
	/**
	 * 上传文件
	 */
	@Test
	public void testPut()  throws Exception{
		//获取文件系统
		FileSystem hdfs = HDFSUtil.getFileSystem();
		Path srcPath = new Path("c:\\unintall.log");
		Path desPath = new Path("/wc/test");
		hdfs.copyFromLocalFile(srcPath, desPath);
	}
	
	
	/**
	 *创建HDFS文件，并写入内容 
	 */
	@Test
	public void testCreateFile() throws Exception {
		//获取文件系统
		FileSystem hdfs = HDFSUtil.getFileSystem();
		Path path = new Path("/wc/test/touch.txt");
		FSDataOutputStream outPutStream = hdfs.create(path);
		//通过输出流写入数据
		outPutStream.writeUTF("Hello,Hadoop");
		IOUtils.closeStream(outPutStream);
	}
	
}