package com.linhongzheng.weixin.message;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.linhongzheng.weixin.entity.user.WeiXinGroup;
import com.linhongzheng.weixin.services.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class UserTest extends AbstractJUnit4SpringContextTests {
	@Resource
	IUserService userService;
	private static final String openId = "o8cS6uEhPb-OVPjNVDCfx1KPw5nE";

//	@Test
	public void testGetUserInfo() {

		System.out.println(userService.getUserInfo(openId));

	}

	//@Test
	public void testBatchGetUserInfos() {
		String[] openIds = new String[] { openId };
		System.out.println(userService.batchGetUserInfos(openIds));
	}

	// @Test
	public void testCreateGroup() {
		userService.createGroup("公司员工");
	}

	//@Test
	public void testUpdateGroup() {
		userService.updateGroup(100, "同事");
	}

	@Test
	public void testGetGroups() {
		List<WeiXinGroup> weiXinGroupList = userService.getGroups();

		Assert.assertNotNull(weiXinGroupList);
		for (WeiXinGroup group : weiXinGroupList) {
			System.out.println(String.format("ID:%s 名称:%s 用户数:%s",
					group.getId(), group.getName(), group.getCount()));
		}
	}

	//@Test
	public void testUpdateUserGroup() {
		userService.updateMemberGroup(openId, 100);
	}

}
