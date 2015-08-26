package com.linhongzheng.weixin.services;

import com.google.inject.ImplementedBy;
import com.linhongzheng.weixin.services.impl.MenuServiceImpl;

@ImplementedBy(MenuServiceImpl.class)
public interface IMenuService {

	public abstract void creatMenu();

}