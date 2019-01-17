package com.canghuang.logincenter.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * 密码加密工具类
 * 登录验证及注册账号
 * @author cs
 * @date 2017年11月2日
 */
public class EncryptUtil {

	private static Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

	/**
	 * 生成随机salt值
	 * @return
	 * @throws Exception
	 */
	public static String createSalt() {
		return md5Encode(UUID.randomUUID().toString()).substring(0, 8);
	}
	
	/**
	 * 加密
	 * @param password
	 * @param salt
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String password, String salt) {
		return md5Encode(password + salt);
	}
	
	/**
	 * MD5加密
	 * @param inStr
	 * @return
	 * @throws Exception
	 */
	public static String md5Encode(String inStr) {
		StringBuffer hexValue = new StringBuffer();
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] byteArray = inStr.getBytes("UTF-8");
			byte[] md5Bytes = md5.digest(byteArray);
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
		} catch (Exception e) {
			logger.error("error occurs:", e);
			return "";
		}
		return hexValue.toString();
	}
}
