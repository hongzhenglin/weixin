package com.linhongzheng.weixin.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 请求校验工具类
 * 
 * @author linhongzheng
 * @date 2015-08-15
 */
public class SignUtil {
	// 与开发模式接口配置信息中的Token保持一致
	private static final String token = "weixintest";

	/**
	 * 
	 * @param signature
	 *            微信加密签名
	 * @param timestamp
	 *            时间戳
	 * @param nonce
	 *            随机数
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp,
			String nonce) {
		// 将token、timestamp、nonce三个参数进行字典序排序
		String[] paramArr = new String[] { token, timestamp, nonce };
		Arrays.sort(paramArr);

		// 将三个参数字符串拼接成一个字符串进行sha1加密
		String content = paramArr[0].concat(paramArr[1].concat(paramArr[2]));

		String ciphertext = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			// 对拼接后的字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			ciphertext = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return ciphertext != null ? ciphertext.equals(signature.toUpperCase())
				: false;
	}

	/**
	 * 将字节数组转换为十六机制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);

		}
		return strDigest;
	}

	/**
	 * 将字节 转换为十六机制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = digit[mByte & 0X0F];
		String s = new String(tempArr);
		return s;
	}

}
