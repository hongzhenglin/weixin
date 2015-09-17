package com.linhongzheng.weixin.message;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linhongzheng.weixin.entity.message.response.CustomResponseMessage;
import com.linhongzheng.weixin.entity.message.response.KfAccoutCustomResponseMessage;
import com.linhongzheng.weixin.entity.message.response.TransInfo;
import com.linhongzheng.weixin.entity.user.WeiXinGroup;
import com.linhongzheng.weixin.entity.user.WeiXinUserInfoList;
import com.linhongzheng.weixin.utils.JSONUtil;
import com.linhongzheng.weixin.utils.XStreamUtil;
import com.thoughtworks.xstream.XStream;

public class JSONTest extends TestCase {
	@Test
	public void testCustomResponse() {
		KfAccoutCustomResponseMessage customResponse = new KfAccoutCustomResponseMessage();
		customResponse.setToUserName("touser");
		customResponse.setFromUserName("fromuser");
		customResponse.setCreateTime(1399197672);
		customResponse.setMsgType("transfer_customer_service");
		TransInfo transInfo = new TransInfo("test1@test");
	 
		customResponse.setTransInfo(transInfo);
		XStream xStream = XStreamUtil.init(true);
		xStream.alias("xml", KfAccoutCustomResponseMessage.class);

		System.out.println(xStream.toXML(customResponse));
	}

	@Test
	public void testCustomResponse2() {
		CustomResponseMessage customResponse = new CustomResponseMessage();
		customResponse.setToUserName("touser");
		customResponse.setFromUserName("fromuser");
		customResponse.setCreateTime(1399197672);
		customResponse.setMsgType("transfer_customer_service");

		XStream xStream = XStreamUtil.init(true);
		xStream.alias("xml", CustomResponseMessage.class);

		System.out.println(xStream.toXML(customResponse));
	}

	public void testUserListData() {
		String data = " { \"total\":23000, \"count\":10000, \"data\":{ \"openid\":[ \"OPENID10001\", \"OPENID10002\", \"OPENID20000\" ] }, \"next_openid\":\"OPENID20000\" }";
		JSONObject jsonObject = JSON.parseObject(data);
		JSONObject openids = JSON.parseObject(jsonObject.getString("data"));

		System.out.println(JSONArray.parseArray(openids.getString("openid"),
				String.class));
	}

	// @Test
	public void testConcatJsonData() {
		List<String> openidList = new ArrayList<String>();
		openidList.add("oDF3iYx0ro3_7jD4HFRDfrjdCM58");
		openidList.add("oDF3iY9FGSSRHom3B-0w5j4jlEyY");
		StringBuilder sb = new StringBuilder();
		for (String openId : openidList) {
			sb.append("\"").append(openId).append("\",");
		}
		int index = sb.toString().length() - 1;
		// String openids = sb.substring(0, index);
		sb.deleteCharAt(index);
		String jsonData = "{\"openid_list\":[ %s],\"to_groupid\":%d}";
		System.out.println(String.format(jsonData, sb.toString(), 100));

	}

	// @Test
	public void testJsonToList() {
		String userInfoListStr = "{ \"user_info_list\": [ { \"subscribe\": 1, \"openid\": \"otvxTs4dckWG7imySrJd6jSi0CWE\", "
				+ "\"nickname\": \"iWithery\", \"sex\": 1, \"language\": \"zh_CN\", \"city\": \"Jieyang\", \"province\": \"Guangdong\","
				+ " \"country\": \"China\", \"headimgurl\": \"http://wx.qlogo.cn/mmopen/xbIQx1GRqdvyqkMMhEaGOX802l1CyqMJNgUzKP8MeAeHFicRDSnZH7FY4XB7p8XHXIf6uJA2SCunTPicGKezDC4saKISzRj3nz/0\", "
				+ "\"subscribe_time\": 1434093047, \"unionid\": \"oR5GjjgEhCMJFyzaVZdrxZ2zRRF4\", \"remark\": \"\", "
				+ "\"groupid\": 0 }, { \"subscribe\": 0, \"openid\": \"otvxTs_JZ6SEiP0imdhpi50fuSZg\", \"unionid\": "
				+ "\"oR5GjjjrbqBZbrnPwwmSxFukE41U\" } ] }";
		WeiXinUserInfoList list = JSONUtil.jsonToObject(userInfoListStr,
				WeiXinUserInfoList.class);
		// System.out.println(list.getUser_info_list().get(0).toString());
	}

	// @Test
	public void testJsonArray() {
		String jsonStr = "{\"groups\":[{\"id\":0,\"name\":\"未分组\",\"count\":72596},{\"id\":1,\"name\":\"黑名单\",\"count\":36},"
				+ "{\"id\":2,\"name\":\"星标组\",\"count\":8}, {\"id\":104,\"name\":\"华东媒\",\"count\":4}]}";
		JSONObject jsonObject = JSON.parseObject(jsonStr);
		System.out.println(JSONArray.parseArray(jsonObject.getString("groups"),
				WeiXinGroup.class));
	}
}
