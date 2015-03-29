package com.onyas.hadoop.tutorial.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

/**
 * HDFS工具类
 *
 */
public class HDFSUtil {

	/**
	 * 获得文件系统
	 * @return
	 */
	public static FileSystem getFileSystem() {

		FileSystem hdfs = null;
		try {
			Configuration conf = new Configuration();
			hdfs = FileSystem.get(conf);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hdfs;
	}

}
