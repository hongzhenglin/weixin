package com.linhongzheng.weixin.entity.user;

import java.util.List;

public class WeiXinUserList {
	private int total; // 关注该公众账号的总用户数
	private int count; // 拉取的OPENID个数，最大值为10000
	private List<String> data; // 列表数据，OPENID的列表
	private String nextOpenId; // 拉取列表的最后一个用户的OPENID

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

	public String getNextOpenId() {
		return nextOpenId;
	}

	public void setNextOpenId(String nextOpenId) {
		this.nextOpenId = nextOpenId;
	}

}
