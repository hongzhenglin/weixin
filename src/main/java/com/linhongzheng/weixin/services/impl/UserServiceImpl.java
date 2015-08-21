package com.linhongzheng.weixin.services.impl;

import com.linhongzheng.weixin.dao.BaseDAO;
import com.linhongzheng.weixin.services.IUserService;

public class UserServiceImpl implements IUserService {

	@Override
	public void saveWeiXinUser(String openId) {

		String insertSql = "INSERT INTO weixin_user(open_id,subscribe_ime,subscribe_status)"
				+ " values(?,now(), 1)";
		new BaseDAO<Integer>().update(insertSql, openId);

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
				+ " date_format(sign_time '%Y-%m-%d') = date_format(now(),' %Y-%m-%d ')";

		boolean flag = false;

		int count = new BaseDAO<Integer>().queryByCount(updateSql, openId);
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
				+ "   sign_time between date_format(? '%Y-%m-%d') and now()";

		boolean flag = false;

		int count = new BaseDAO<Integer>().queryByCount(updateSql, openId,
				monday);

		if (count == 1) {
			flag = true;
		} else {
			flag = false;
		}

		return flag;
	}
}
