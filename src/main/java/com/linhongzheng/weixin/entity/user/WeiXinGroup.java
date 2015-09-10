package com.linhongzheng.weixin.entity.user;

public class WeiXinGroup {
	private int id; // 分组id，由微信分配
	private String name; // 分组名字，UTF8编码
	private int count; // 分组内用户数量
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
