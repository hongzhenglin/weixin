package com.linhongzheng.weixin.services;

public interface IMenuService {

	public abstract void creatMenu();

	public abstract void creatMenu(String jsonMenu);

	public abstract String queryMenu();

	public abstract String deleteMenu();

}