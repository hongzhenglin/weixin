package com.linhongzheng.weixin.entity.message;

import org.junit.Test;

import com.linhongzheng.weixin.entity.message.MSG_TYPE;

public class MsgTypeTest {

	@Test
	public void test() {
		String msgType = "text";
		switch (MSG_TYPE.valueOf(msgType.toUpperCase())) {
			case TEXT:    // 文本消息
				System.out.println("text");
				break;
			default: System.out.println("default");break;
		}
	}

}
