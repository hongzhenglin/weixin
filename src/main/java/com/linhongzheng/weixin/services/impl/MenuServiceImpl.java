package com.linhongzheng.weixin.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.linhongzheng.weixin.entity.menu.Button;
import com.linhongzheng.weixin.entity.menu.ClickButton;
import com.linhongzheng.weixin.entity.menu.ComplexButton;
import com.linhongzheng.weixin.entity.menu.Menu;
import com.linhongzheng.weixin.entity.menu.ViewButton;
import com.linhongzheng.weixin.services.AbstractWeChatService;
import com.linhongzheng.weixin.services.IAccessTokenService;
import com.linhongzheng.weixin.services.IMenuService;
import com.linhongzheng.weixin.utils.HttpUtil;
import com.linhongzheng.weixin.utils.JSONUtils;
import com.linhongzheng.weixin.utils.URLConstants;

/**
 * Created by linhz on 2015/8/17.
 */
public class MenuServiceImpl extends AbstractWeChatService implements
		IMenuService {
	private static Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);

	@Inject
	IAccessTokenService accessTokenService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.linhongzheng.weixin.services.impl.IMenuService#creatMenu()
	 */
	@Override
	public void creatMenu() {

		String at;
		try {
			at = accessTokenService.getAccessToken();
			if (null != at) {
				String requestUrl = URLConstants.MENU_CREATE_URL.replace(
						"ACCESS_TOKEN", at);
				String jsonMenu = JSONUtils.objectToJson(getMenu(), "0");
				String jsonStr = HttpUtil.post(requestUrl, jsonMenu);

				System.out.println(jsonStr);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Menu getMenu() {

		ClickButton btn1 = new ClickButton();
		btn1.setName("今日歌曲");
		btn1.setType("click");
		btn1.setKey("V1001_TODAY_MUSIC");

		ViewButton btn2 = new ViewButton();
		btn2.setName("歌手简介");
		btn2.setType("View");
		btn2.setUrl("http://www.qq.com");

		ClickButton btn31 = new ClickButton();
		btn31.setName("Hello world");
		btn31.setType("click");
		btn31.setKey("V1001_HELLO_WORLD");

		ClickButton btn32 = new ClickButton();
		btn32.setName("赞一下我们");
		btn32.setType("click");
		btn32.setKey("V1001_GOOD");

		ComplexButton btn3 = new ComplexButton();
		btn3.setName("生活助手");
		btn3.setSub_button(new Button[] { btn31, btn32 });

		Menu menu = new Menu();
		menu.setButton(new Button[] { btn1, btn2, btn3 });

		return menu;
	}
}
