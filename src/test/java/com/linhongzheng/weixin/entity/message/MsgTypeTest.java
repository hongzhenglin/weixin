package com.linhongzheng.weixin.entity.message;

import org.junit.Test;

import com.linhongzheng.weixin.entity.message.request.REQ_MSG_TYPE;

public class MsgTypeTest {

	@Test
	public void test() {

		assert (REQ_MSG_TYPE.TEXT.equals("text"));
	}

}
