package com.linhongzheng.weixin.message;

import org.junit.Test;

import com.linhongzheng.weixin.message.request.REQ_MSG_TYPE;

public class MsgTypeTest {

	@Test
	public void test() {

		assert (REQ_MSG_TYPE.TEXT.getDesc().equals("text"));
	}

}
