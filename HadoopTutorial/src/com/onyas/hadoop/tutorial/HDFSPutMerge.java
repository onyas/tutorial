package com.onyas.hadoop.tutorial;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * 功能：在向HDFS文件系统中传文件的时候进行合并
 *
 */
public class HDFSPutMerge {

	/**
	 * 复制上传文件，并将文件合并
	 * 
	 * @param localDir
	 *            本地要上传的文件目录
	 * @param hdfsFile
	 *            HDFS上的文件名称(全路径)
	 * @throws IOException
	 */
	public static void putMerge(String localDir, String hdfsFile) {
		// 1、获取配置信息
		Configuration conf = new Configuration();
		// 2、封装路径信息
		Path localPath = new Path(localDir);
		Path hdfsPath = new Path(hdfsFile);
		try {
			// 3、获取本地文件系统
			FileSystem localFs = FileSystem.getLocal(conf);
			// 获取HDFS
			FileSystem hdfs = FileSystem.get(conf);

			// 4、得到HDFS文件输出流，用于写入HDFS文件中
			FSDataOutputStream fsDataOutputStream = hdfs.create(hdfsPath);

			// 5、遍历本地文件，并打开输入流读取文件内容
			FileStatus[] status = localFs.listStatus(localPath);
			for (FileStatus fileStatus : status) {
				Path path = fileStatus.getPath();
				System.out.println("正在读取" + path.getName());

				// 6、打开本地文件的输入流，开始读取文件
				FSDataInputStream fsDataInputStream = localFs.open(path);

				// 7、把输入流的内容写入到输出流，完成文件的上传
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fsDataInputStream.read(buffer)) > 0) {
					fsDataOutputStream.write(buffer, 0, len);
				}

				// 8、每完成一个文件的读取，就关闭该文件的输入流
				fsDataInputStream.close();
			}
			// 9、所有文件读取完成后，关闭输出流
			fsDataOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String localDir = "C:/temp";
		String hdfsFile = "hdfs://hadoop-master:9000/wc/test/logs.data";
		putMerge(localDir, hdfsFile);
	}
}
