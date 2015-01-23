package com.onyas.zk.dsession;

import java.util.UUID;

/**
 * @author hxpwangyi@163.com
 * @date 2013-3-1
 */
public class SidGenerator {
	public static String generateSid(){
		return UUID.randomUUID().toString();
	}
}
