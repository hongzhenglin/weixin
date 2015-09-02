package com.linhongzheng.weixin.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密 采用java.security.MessageDigest
 * 
 * @author chillming
 */
public class Md5Util {

	/**
	 * MD5加密
	 * 
	 * @param apcIn
	 *            明文
	 * @return 十六进制的MD5密文串
	 */
	public static String encrypt(String apcIn) {
		byte[] buff = md5Digest(apcIn.getBytes());
		return StringUtil.byte2hex(buff);
	}

	/**
	 * MD5加密
	 * 
	 * @param file
	 * @return 十六进制的MD5密文串
	 */
	public static String encrypt(File file) {
		String md5 = null;
		FileInputStream fis = null;
		FileChannel fileChannel = null;
		try {
			fis = new FileInputStream(file);
			fileChannel = fis.getChannel();
			MappedByteBuffer byteBuffer = fileChannel.map(
					FileChannel.MapMode.READ_ONLY, 0, file.length());

			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(byteBuffer);
				md5 = StringUtil.byte2hex(md.digest());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileChannel.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return md5;
	}

	/**
	 * MD5 摘要计算(byte[]).
	 * 
	 * @param src
	 *            byte[]
	 * @throws Exception
	 * @return byte[] 16 bit digest
	 */
	public static byte[] md5Digest(byte[] src) {
		java.security.MessageDigest alg = null;
		try {
			alg = java.security.MessageDigest.getInstance("MD5");

		} catch (NoSuchAlgorithmException e) {

		}
		return alg.digest(src);
	}
}
