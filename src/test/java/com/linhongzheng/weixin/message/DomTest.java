package com.linhongzheng.weixin.message;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;

import com.linhongzheng.weixin.utils.message.MessageUtil;

public class DomTest extends TestCase {

	@Test
	public void testParseXml() {

		try {
			Map<String, String> map = MessageUtil
					.parseInXml(new FileInputStream("test.xml"));
		 
			assert (map.get("MsgId").equals("1234567890123456"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
