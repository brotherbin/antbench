package com.brotherbin.antbench.base;

import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class RandomTool {

	/**
	 * 获取随机整数
	 * @return
	 */
	public int getInt() {
		return RandomUtils.nextInt(1, Integer.MAX_VALUE);
	}
	
	/**
	 * 获取随机字符串，由字母和数字组成，长度为1-128位
	 * @return
	 */
	public String getStr() {
		return RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(1, 128));
	}
	
	/**
	 * 获取UUID
	 * @return
	 */
	public String getStrPk() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
