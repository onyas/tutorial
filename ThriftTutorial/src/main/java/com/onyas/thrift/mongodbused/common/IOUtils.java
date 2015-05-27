package com.onyas.thrift.mongodbused.common;

import java.io.Closeable;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtils {
	
	private static transient Logger LOGGER = LoggerFactory.getLogger(IOUtils.class);

	/**
	 * 关闭可关闭的IO对象
	 * 
	 * @param closeable
	 *            Closeable
	 */
	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				LOGGER.error("关闭对象" + closeable.getClass().getName() + "出错！",e);
			} finally {
				closeable = null;
			}
		}
	}

}