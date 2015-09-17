package com.linhongzheng.weixin.message;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.linhongzheng.weixin.services.IGroupMessageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class GroupMessageTest extends AbstractJUnit4SpringContextTests {

	@Resource
	IGroupMessageService groupMessageService;

	//@Test
	public void testSendGroupMessageByOpenIds() {
		String jsonData = "{ \"touser\":[ \"o8cS6uEhPb-OVPjNVDCfx1KPw5nE\", \"o8cS6uEhPb-OVPjNVDCfx1KPw5nE\"], \"image\":{ \"media_id\":\"RKP94pIQN_KvpJ52zUYfGBMPLP4Ec-Yjg0vsqvSAgCsYBeUvvzlDU2hh77h-MCJ1\" }, \"msgtype\":\"image\" }";
		groupMessageService.sendMessageByOpenids(jsonData);
		//{"errcode":0,"errmsg":"send job submission success","msg_id":2357192801}
	}

	// @Test
	public void testSendGroupMessageByGroup() {
		String jsonData = "{ \"filter\":{ \"is_to_all\":false, \"group_id\":\"0\" }, \"image\":{ \"media_id\":\"DZx4YASq4olvQ8by9mOIjLHJ6O7MpwMEHqOnVZMjrq7SyL8TaZ7SoU02-u2EdDfc\" }, \"msgtype\":\"image\" }";
		// String jsonData
		// ="{ \"filter\":{ \"is_to_all\":false ,\"group_id\":\"0\" }, \"text\":{ \"content\":\"群发消息测试\"},\"msgtype\":\"text\" }";
		groupMessageService.sendMessageByGroupid(jsonData);
	}
	
	@Test
	public void testDeleteSendMessageStatus(){
		groupMessageService.deleteMessage("2357192801");
	}
	
	@Test
	public void testQuerySendMessageStatus(){
		String resp = groupMessageService.queryMessageSendStatus("2357192801");
		System.out.println("查询群发消息状态："+resp);
	}
}
