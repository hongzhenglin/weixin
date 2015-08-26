package com.linhongzheng.weixin.services;

import com.google.inject.ImplementedBy;
import com.linhongzheng.weixin.services.impl.UserServiceImpl;

@ImplementedBy(UserServiceImpl.class)
public interface IUserService {
	public abstract void saveWeiXinUser(String openId);

	public abstract void updateUserPoints(String openId, int signPoints);

	public abstract void saveWeiXinSign(String openId, int signPoints);

	public abstract boolean isTodaySign(String openId );
	
	public abstract boolean isWeekySign(String openId,String monday);
}
