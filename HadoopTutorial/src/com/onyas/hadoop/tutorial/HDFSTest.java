package com.onyas.hadoop.tutorial;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import com.onyas.hadoop.tutorial.util.HDFSUtil;

public class HDFSTest {

	@Test
	public void testList() throws Exception {
		FileSystem hdfs = HDFSUtil.getFileSystem();
		Path path = new Path("/");
		FileStatus[] fileStatus = hdfs.listStatus(path);
		for(FileStatus fs :fileStatus){
			Path p = fs.getPath();
			String info = fs.isDir()?"目录":"文件";
			System.out.println(info+":"+p);
		}
	}
	
}
