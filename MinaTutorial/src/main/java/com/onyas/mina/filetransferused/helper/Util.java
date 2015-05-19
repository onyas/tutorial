package com.onyas.mina.filetransferused.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;

public class Util {
	private static Logger logger = Logger.getLogger(Util.class);

	public static long getFileLength(String filePath) {
		File file = new File(filePath);
		return file.length();
	}

	public static IoSession delFilter(IoSession session) {
		if (session.getFilterChain().contains(Constant.FILTER_OBJ)) {
			session.getFilterChain().remove(Constant.FILTER_OBJ);
		}
		return session;
	}

	public static IoSession addFilter(IoSession session) {
		if (!session.getFilterChain().contains(Constant.FILTER_OBJ)) {
			session.getFilterChain().addLast(
					Constant.FILTER_OBJ,
					new ProtocolCodecFilter(
							new ObjectSerializationCodecFactory()));
		}
		return session;
	}

	/*
	 * 文件生成字符串
	 */
	public static String getStringFromFile(String filename, String encoding)

	{
		StringBuffer strbuf = new StringBuffer();
		try {
			File file = new File(filename);

			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTXT = null;
				int kk = 0;
				while ((lineTXT = bufferedReader.readLine()) != null) {
					strbuf.append(lineTXT);
				}
				read.close();
			} else {
				logger.info("找不到指定的文件");
			}
		} catch (Exception e) {
			logger.error("读取文件内容操作出错", e);
		}
		return strbuf.toString();
	}

	/*
	 * 字符串生成文件
	 */
	public static File getFileFromString(String content, File file, String id,
			String encoding) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file, false), encoding));
			writer.write(content);
		} catch (Exception e) {
			logger.error("[ID:" + id + "] " + "全文检索生成临时文件出错!", e);
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					logger.error("[ID:" + id + "] " + "全文检索生成临时文件出错!", e);
				}
		}
		return file;
	}

	public static void copyFile(File srcFileName, File destFileName,
			String srcCoding, String destCoding) {// 把文件转换为GBK文件
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					srcFileName), srcCoding));
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(destFileName), destCoding));
			char[] cbuf = new char[1024 * 5];
			int len = cbuf.length;
			int off = 0;
			int ret = 0;
			while ((ret = br.read(cbuf, off, len)) > 0) {
				off += ret;
				len -= ret;
			}
			bw.write(cbuf, 0, off);
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (bw != null)
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
