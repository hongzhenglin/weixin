package com.linhongzheng.weixin.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 请求校验工具类
 * 
 * @author linhongzheng
 * @date 2015-08-15
 */
public class SignUtil {

	// 与开发模式接口配置信息中的Token保持一致
	public static final String Token = "weixintest";
	public static final String EncodingAesKey = "hkUNxNc8KBakZaCnRv9dwx6Gcj3rtIDNrVmzplHLZIc";

	/**
	 * msg_signature=sha1(sort(Token、timestamp、nonce, msg_encrypt))
	 * 
	 * @return
	 */
	public static boolean checkCryptSignature(String msgSignature,
			String timestamp, String nonce, String msgEncrypt) {
		// 将token、timestamp、nonce、msg_encrypt四个参数进行字典序排序
		String[] paramArr = new String[] { Token, timestamp, nonce, msgEncrypt };
		Arrays.sort(paramArr);
		// 将四个参数字符串拼接成一个字符串进行sha1加密
		String content = paramArr[0].concat(paramArr[1].concat(paramArr[2]
				.concat(paramArr[3])));
		return shaDigest(msgSignature, content);
	}

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
		String[] paramArr = new String[] { Token, timestamp, nonce };
		Arrays.sort(paramArr);

		// 将三个参数字符串拼接成一个字符串进行sha1加密
		String content = paramArr[0].concat(paramArr[1].concat(paramArr[2]));

		return shaDigest(signature, content);
	}

	private static boolean shaDigest(String signature, String content) {
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

	//以下为js-sdk的签名相关方法
	/**
	 * 
	 * @param jsapi_ticket
	 * @param url
	 * @return
	 */
	public static Map<String, String> sign(String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str
				+ "&timestamp=" + timestamp + "&url=" + url;
		System.out.println(string1);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	public static void main(String[] args) {
		String jsapi_ticket = "sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg";

		// 注意 URL 一定要动态获取，不能 hardcode
		String url = "http://example.com?params=value";
		Map<String, String> ret = sign(jsapi_ticket, url);
		for (Map.Entry entry : ret.entrySet()) {
			System.out.println(entry.getKey() + ", " + entry.getValue());
		}
	}
}
