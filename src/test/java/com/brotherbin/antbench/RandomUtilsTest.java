package com.brotherbin.antbench;

import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;

public class RandomUtilsTest {
	
	@Test
	public void testTandomUtils() {
		int randomInt = RandomUtils.nextInt(1, 99999999);
		Assert.assertTrue(randomInt > 1);
		Assert.assertTrue(randomInt < 99999999);
		String randomStr = RandomStringUtils.randomAlphanumeric(20);
		Assert.assertEquals(20, randomStr.length());
	}
	
	@Test
	public void testGenUUID() {
		String str = UUID.randomUUID().toString();
		System.out.println(str);
		int id = UUID.randomUUID().hashCode();
		System.out.println(id);
	}
	
}
