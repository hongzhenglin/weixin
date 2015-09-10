package com.linhongzheng.weixin.services;

import java.util.List;

import com.linhongzheng.weixin.entity.user.WeiXinGroup;
import com.linhongzheng.weixin.entity.user.WeiXinUserInfo;
import com.linhongzheng.weixin.entity.user.WeiXinUserList;

public interface IUserService {

	public abstract void saveWeiXinUser(String openId);

	public abstract void updateWeiXinUserStatus(String openId, int status);

	public abstract void updateUserPoints(String openId, int signPoints);

	public abstract void saveWeiXinSign(String openId, int signPoints);

	public abstract boolean isTodaySign(String openId);

	public abstract boolean isWeekySign(String openId, String monday);

	public abstract WeiXinGroup createGroup(String groupName);

	public abstract List<WeiXinGroup> getGroups();

	public abstract int getUserGroup(String openId);

	public abstract boolean updateGroup(int groupId, String groupName);

	public abstract boolean updateMemberGroup(String openId, int groupId);

	public abstract boolean batchUpdateUserGroup(String[] openids, int groupId);

	public abstract boolean deleteGroup(int groupId);

	public abstract boolean updateUserRemark(String openId, String userRemark);

	// 获取用户基本信息
	public abstract WeiXinUserInfo getUserInfo(String openId);

	// 批量获取用户基本信息
	public abstract List<WeiXinUserInfo> batchGetUserInfos(String[] openIds);

	// 获取用户（关注者）列表
	public abstract WeiXinUserList getUserList(String nextOpenId);
}
