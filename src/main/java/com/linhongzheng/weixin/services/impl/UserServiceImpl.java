package com.linhongzheng.weixin.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linhongzheng.weixin.dao.BaseDAO;
import com.linhongzheng.weixin.entity.user.WeiXinGroup;
import com.linhongzheng.weixin.entity.user.WeiXinUserInfo;
import com.linhongzheng.weixin.entity.user.WeiXinUserInfoList;
import com.linhongzheng.weixin.entity.user.WeiXinUserList;
import com.linhongzheng.weixin.services.AbstractWeChatService;
import com.linhongzheng.weixin.services.ITokenService;
import com.linhongzheng.weixin.services.IUserService;
import com.linhongzheng.weixin.utils.CommonUtil;
import com.linhongzheng.weixin.utils.JSONUtil;
import com.linhongzheng.weixin.utils.URLConstants;

@Service("userService")
public class UserServiceImpl extends AbstractWeChatService implements
		IUserService {
	private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	ITokenService tokenService;

	@Override
	public void saveWeiXinUser(String openId) {

		String insertSql = "INSERT INTO weixin_user(open_id,subscribe_time,subscribe_status)"
				+ " values(?, now(), 1)";
		new BaseDAO<Integer>().update(insertSql, openId);

	}

	@Override
	public void updateWeiXinUserStatus(String openId, int status) {
		String updateSql = "UPDATE  weixin_user SET subscribe_status= ?  WHERE open_id= ?";
		new BaseDAO<Integer>().update(updateSql, status, openId);
	}

	@Override
	public void updateUserPoints(String openId, int signPoints) {

		String updateSql = "update weixin_user set points =points+? where open_id=?";

		new BaseDAO<Integer>().update(updateSql, signPoints, openId);

	}

	@Override
	public void saveWeiXinSign(String openId, int signPoints) {

		String insertSql = "INSERT INTO weixin_sign(open_id,sign_time,sign_points)"
				+ " values(?,now(), ?)";

		new BaseDAO<Integer>().update(insertSql, openId, signPoints);

	}

	@Override
	// 今天是否签到
	public boolean isTodaySign(String openId) {

		String updateSql = "SELECT COUNT(*) FROM weixin_sign WHERE  open_id = ? AND "
				+ " DATEDIFF(date_format(sign_time ,'%Y-%m-%d') , date_format(now(),' %Y-%m-%d'))=0";

		boolean flag = false;

		long count = new BaseDAO<Integer>().queryByCount(updateSql, openId);
		if (count == 1) {
			flag = true;
		} else {
			flag = false;
		}

		return flag;
	}

	@Override
	// 是否本周第7次签到
	public boolean isWeekySign(String openId, String monday) {

		String updateSql = "select count(*)  from weixin_sign where  open_id = ? AND"
				+ "   sign_time between date_format(?, '%Y-%m-%d') and now()";

		boolean flag = false;

		long count = new BaseDAO<Integer>().queryByCount(updateSql, openId,
				monday);

		if (count == 1) {
			flag = true;
		} else {
			flag = false;
		}

		return flag;
	}

	/**
	 * 创建用户组
	 * 
	 * @param groupName
	 *            用户组名
	 * @return 返回用户组信息
	 */
	@Override
	public WeiXinGroup createGroup(String groupName) {
		String jsonData = createGroupRequest(groupName);
		String at = null;
		try {
			at = tokenService.getAccessToken();
		} catch (Exception e) {

			e.printStackTrace();
		}
		WeiXinGroup weiXinGroup = null;
		if (null != at) {
			String requestUrl = URLConstants.USER.CREATE_GROUP_URL.replace(
					"ACCESS_TOKEN", at);

			String jsonResp = CommonUtil.httpsRequest(requestUrl, "POST",
					jsonData);
			if (jsonResp != null) {
				JSONObject jsonObject = JSON.parseObject(jsonResp);
				int errcode = jsonObject.getIntValue("errcode");
				if (errcode != 0) {
					log.error("创建用户组失败：" + jsonObject.getString("errmsg"));
				} else {
					weiXinGroup = parseGroup(jsonObject);

				}
			}
		}
		return weiXinGroup;
	}

	/**
	 * 获取公众号创建的所有分组，包括微信创建的默认分组
	 */
	@Override
	public List<WeiXinGroup> getGroups() {
		String at = null;
		try {
			at = tokenService.getAccessToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<WeiXinGroup> weiXinGroupList = null;
		if (null != at) {
			String requestUrl = URLConstants.USER.GET_GROUPS_URL.replace(
					"ACCESS_TOKEN", at);
			String jsonResp = CommonUtil.httpsRequest(requestUrl, "GET", null);
			if (jsonResp != null) {
				JSONObject jsonObject = JSON.parseObject(jsonResp);
				int errcode = jsonObject.getIntValue("errcode");
				if (errcode != 0) {
					log.error("获取用户组列表失败：" + jsonObject.getString("errmsg"));
				} else {
					weiXinGroupList = JSONArray.parseArray(
							jsonObject.getString("groups"), WeiXinGroup.class);
				}
			}
		}

		return weiXinGroupList;
	}

	/**
	 * 获取用户所在的组
	 * 
	 * @param openId
	 *            用户的ID
	 * @return 用户所在的组ID
	 */
	@Override
	public int getUserGroup(String openId) {
		int groupId = 0;
		String at = null;
		try {
			at = tokenService.getAccessToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != at) {
			String requestUrl = URLConstants.USER.GETID_USERGROUP_URL.replace(
					"ACCESS_TOKEN", at);
			String jsonData = "{\"openid\":\"%s\"}";
			String jsonResp = CommonUtil.httpsRequest(requestUrl, "POST",
					String.format(jsonData, openId));
			if (jsonResp != null) {
				JSONObject jsonObject = JSON.parseObject(jsonResp);
				int errcode = jsonObject.getIntValue("errcode");
				if (errcode != 0) {
					log.error("获取用户所在组失败：errorcode:{} errmsg{}", errcode,
							jsonObject.getString("errmsg"));
				} else {
					groupId = jsonObject.getIntValue("groupid");
				}
			}
		}
		return groupId;

	}

	/**
	 * 修改分组名
	 * 
	 * @param groupId
	 *            组ID
	 * @param groupName
	 *            组名称
	 * @return
	 */
	@Override
	public boolean updateGroup(int groupId, String groupName) {
		boolean result = false;
		String at = null;
		try {
			at = tokenService.getAccessToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != at) {
			String requestUrl = URLConstants.USER.UPDATE_GROUP_URL.replace(
					"ACCESS_TOKEN", at);
			String jsonData = "{\"group\":{\"id\":%d,\"name\":\"%s\"}}";
			String jsonResp = CommonUtil.httpsRequest(requestUrl, "POST",
					String.format(jsonData, groupId, groupName));
			if (jsonResp != null) {
				JSONObject jsonObject = JSON.parseObject(jsonResp);
				int errcode = jsonObject.getIntValue("errcode");
				String errmsg = jsonObject.getString("errmsg");
				if (errcode != 0) {
					log.error("修改分组名失败：errorcode:{} errmsg{}", errcode, errmsg);
				} else {
					result = true;
					log.info("修改分组名成功：errorcode:{} errmsg{}", errcode, errmsg);
				}
			}
		}
		return result;

	}

	/**
	 * 移动所在的组
	 * 
	 * @param openId
	 *            用户ID
	 * @param groupId
	 *            用户组ID
	 */
	@Override
	public boolean updateMemberGroup(String openId, int groupId) {
		boolean result = false;
		String at = null;
		try {
			at = tokenService.getAccessToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != at) {
			String requestUrl = URLConstants.USER.UPDATE_USERGROUP_URL.replace(
					"ACCESS_TOKEN", at);
			String jsonData = "{\"openid\":\"%s\",\"to_groupid\":%d}";
			String jsonResp = CommonUtil.httpsRequest(requestUrl, "POST",
					String.format(jsonData, openId, groupId));
			if (jsonResp != null) {
				JSONObject jsonObject = JSON.parseObject(jsonResp);
				int errcode = jsonObject.getIntValue("errcode");
				String errmsg = jsonObject.getString("errmsg");
				if (errcode != 0) {
					log.error("移动所在的组失败：errorcode:{} errmsg{}", errcode, errmsg);
				} else {
					result = true;
					log.info("移动所在的组成功：errorcode:{} errmsg{}", errcode, errmsg);
				}
			}
		}
		return result;

	}

	/**
	 * 批量移动用户所在的组 一次最多移动50个用户
	 * 
	 * @param openidList
	 * @param groupId
	 */
	@Override
	public boolean batchUpdateUserGroup(String[] openids, int groupId) {
		boolean result = false;
		String at = null;
		try {
			at = tokenService.getAccessToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		for (String openId : openids) {
			sb.append("\"").append(openId).append("\",");
		}
		int index = sb.toString().length() - 1;
		sb.deleteCharAt(index);
		;

		String jsonData = "{\"openid_list\":[ %s],\"to_groupid\":%d}";
		if (null != at) {
			String requestUrl = URLConstants.USER.BATUPADATE_USERGRUP_URL
					.replace("ACCESS_TOKEN", at);
			String jsonResp = CommonUtil.httpsRequest(requestUrl, "POST",
					String.format(jsonData, sb.toString(), groupId));
			if (jsonResp != null) {
				JSONObject jsonObject = JSON.parseObject(jsonResp);
				int errcode = jsonObject.getIntValue("errcode");
				String errmsg = jsonObject.getString("errmsg");
				if (errcode != 0) {
					log.error("批量移动用户所在的组 失败：errorcode:{} errmsg{}", errcode,
							errmsg);
				} else {
					result = true;
					log.info("批量移动用户所在的组 成功：errorcode:{} errmsg{}", errcode,
							errmsg);
				}
			}
		}
		return result;
	}

	/**
	 * 删除组
	 * 
	 * @param groupid
	 *            分组名
	 */
	@Override
	public boolean deleteGroup(int groupId) {
		boolean result = false;
		String at = null;
		try {
			at = tokenService.getAccessToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != at) {
			String requestUrl = URLConstants.USER.DELETE_GROUP_URL.replace(
					"ACCESS_TOKEN", at);
			String jsonData = "	{\"group\":{\"id\":%d}}";
			String jsonResp = CommonUtil.httpsRequest(requestUrl, "POST",
					String.format(jsonData, groupId));
			if (jsonResp != null) {
				JSONObject jsonObject = JSON.parseObject(jsonResp);
				int errcode = jsonObject.getIntValue("errcode");
				String errmsg = jsonObject.getString("errmsg");
				if (errcode != 0) {
					log.error("删除组名失败：errorcode:{} errmsg{}", errcode, errmsg);
				} else {
					result = true;
					log.info("删除组名成功：errorcode:{} errmsg{}", errcode, errmsg);
				}
			}
		}
		return result;

	}

	/**
	 * 设置用户备注名
	 * 
	 * @param openId
	 *            用户ID
	 * @param userRemark
	 *            用户备注名
	 */
	@Override
	public boolean updateUserRemark(String openId, String userRemark) {
		boolean result = false;
		String at = null;
		try {
			at = tokenService.getAccessToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != at) {
			String requestUrl = URLConstants.USER.UPDATE_USERREMARK_URL
					.replace("ACCESS_TOKEN", at);
			String jsonData = "{\"openid\":\"%s\", \"remark\":\"%s\" }";
			String jsonResp = CommonUtil.httpsRequest(requestUrl, "POST",
					String.format(jsonData, openId, userRemark));
			if (jsonResp != null) {
				JSONObject jsonObject = JSON.parseObject(jsonResp);
				int errcode = jsonObject.getIntValue("errcode");
				String errmsg = jsonObject.getString("errmsg");
				if (errcode != 0) {
					log.error("设置用户备注名失败：errorcode:{} errmsg{}", errcode,
							errmsg);
				} else {
					result = true;
					log.info("设置用户备注名成功：errorcode:{} errmsg{}", errcode, errmsg);
				}
			}
		}
		return result;
	}

	/**
	 * 获取用户基本信息
	 * 适合用户在关注的时候，回复一条欢迎关注+用户昵称的信息
	 * @param openId
	 */
	@Override
	public WeiXinUserInfo getUserInfo(String openId) {
		String at = null;
		try {
			at = tokenService.getAccessToken();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WeiXinUserInfo userInfo = null;
		if (null != at) {
			String requestUrl = URLConstants.USER.GET_USERINFO_URL.replace(
					"ACCESS_TOKEN", at).replace("OPENID", openId);

			String jsonStr = CommonUtil.httpsRequest(requestUrl, "GET", null);
			if (jsonStr != null) {
				JSONObject jsonObject = JSON.parseObject(jsonStr);
				int errcode = jsonObject.getIntValue("errcode");
				if (errcode != 0) {
					log.error("获取用户基本信息失败：" + jsonObject.getString("errmsg"));
				} else {
					userInfo = createUserInfo(jsonObject);
					if (userInfo != null && userInfo.getSubscribe() == 0) {
						log.error("用户{}已取消关注", userInfo.getOpenid());
						updateWeiXinUserStatus(userInfo.getOpenid(), 0);
					}
				}
			}
		}
		return userInfo;
	}

	/**
	 * 批量获取用户基本信息
	 * 
	 * @param openIds
	 */
	@Override
	public List<WeiXinUserInfo> batchGetUserInfos(String[] openIds) {
		String postData = createBatchGetUserInfoRequest(openIds);
		String at = null;
		WeiXinUserInfoList list = null;
		try {
			at = tokenService.getAccessToken();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null != at) {
			String requestUrl = URLConstants.USER.BATCHGET_USERINFO_URL
					.replace("ACCESS_TOKEN", at);

			String jsonStr = CommonUtil.httpsRequest(requestUrl, "POST",
					postData);
			if (jsonStr != null) {
				JSONObject jsonObject = JSON.parseObject(jsonStr);
				int errcode = jsonObject.getIntValue("errcode");
				if (errcode != 0) {
					log.error("批量获取用户基本信息失败：" + jsonObject.getString("errmsg"));
				} else {
					list = JSONUtil.jsonToObject(jsonStr,
							WeiXinUserInfoList.class);
				}
			}
		}
		return list.getUser_info_list();
	}

	/**
	 * 获取关注者列表
	 * 
	 * @param nextOpenId
	 * @return
	 */
	@Override
	public WeiXinUserList getUserList(String nextOpenId) {
		String at = null;
		try {
			at = tokenService.getAccessToken();
		} catch (Exception e) {

			e.printStackTrace();
		}
		WeiXinUserList weiXinUserList = null;
		if (null != at) {
			String requestUrl = URLConstants.USER.BATCHGET_USER_URL.replace(
					"ACCESS_TOKEN", at).replace("NEXT_OPENID", nextOpenId);

			String jsonStr = CommonUtil.httpsRequest(requestUrl, "GET", null);
			if (jsonStr != null) {
				JSONObject jsonObject = JSON.parseObject(jsonStr);
				int errcode = jsonObject.getIntValue("errcode"); // 如果没有errcode,则返回0
				if (errcode != 0) {
					log.error("批量获取用户基本信息失败：" + jsonObject.getString("errmsg"));
				} else {
					weiXinUserList = new WeiXinUserList();
					weiXinUserList.setCount(jsonObject.getIntValue("count"));
					weiXinUserList.setTotal(jsonObject.getIntValue("total"));
					// 如果next_openid为空，则表明已经没有数据，否则可以递归获取
					weiXinUserList.setNextOpenId(jsonObject
							.getString("next_openid"));
					JSONObject openids = JSON.parseObject(jsonObject
							.getString("data"));
					weiXinUserList.setData(JSONArray.parseArray(
							openids.getString("openid"), String.class));
				}
			}
		}
		return weiXinUserList;
	}

	private String createGroupRequest(String groupName) {
		String jsonData = "{\"group\":{\"name\":\"%s\"}}";
		return String.format(jsonData, groupName);
	}

	private WeiXinGroup parseGroup(JSONObject jsonObject) {
		WeiXinGroup weiXinGroup = new WeiXinGroup();
		weiXinGroup.setId(jsonObject.getJSONObject("group").getIntValue("id"));
		weiXinGroup
				.setName(jsonObject.getJSONObject("group").getString("name"));
		return weiXinGroup;
	}

	private String createBatchGetUserInfoRequest(String[] openIds) {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"user_list\": [");
		if (openIds != null && openIds.length > 0) {
			for (String openId : openIds) {
				sb.append("{\"openid\":\"").append(openId)
						.append("\",\"lang\": \"zh-CN\" },");
			}
			int index = sb.toString().length() - 1;
			sb.deleteCharAt(index);
		}

		sb.append("]}");
		return sb.toString();
	}

	private WeiXinUserInfo createUserInfo(JSONObject jsonObject) {
		WeiXinUserInfo userInfo = new WeiXinUserInfo();
		userInfo.setSubscribe(jsonObject.getIntValue("subscribe"));
		userInfo.setOpenid(jsonObject.getString("openid"));
		userInfo.setNickname(jsonObject.getString("nickname"));
		userInfo.setSex(jsonObject.getIntValue("sex"));
		userInfo.setCity(jsonObject.getString("city"));
		userInfo.setCountry(jsonObject.getString("country"));
		userInfo.setProvince(jsonObject.getString("province"));
		userInfo.setLanguage(jsonObject.getString("language"));
		userInfo.setHeadimgurl(jsonObject.getString("headimgurl"));
		userInfo.setSubscribeTime(jsonObject.getLongValue("subscribe_time"));
		userInfo.setUnionid(jsonObject.getString("unionid"));
		userInfo.setRemark(jsonObject.getString("remark"));
		userInfo.setGroupid(jsonObject.getIntValue("groupid"));
		return userInfo;
	}

	public ITokenService getTokenService() {
		return tokenService;
	}

	public void setTokenService(ITokenService tokenService) {
		this.tokenService = tokenService;
	}
}
