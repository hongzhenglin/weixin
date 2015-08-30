package com.linhongzheng.weixin.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linhongzheng.weixin.entity.menu.Button;
import com.linhongzheng.weixin.entity.menu.ClickButton;
import com.linhongzheng.weixin.entity.menu.ComplexButton;
import com.linhongzheng.weixin.entity.menu.Menu;
import com.linhongzheng.weixin.entity.menu.ViewButton;
import com.linhongzheng.weixin.services.AbstractWeChatService;
import com.linhongzheng.weixin.services.IAccessTokenService;
import com.linhongzheng.weixin.services.IMenuService;
import com.linhongzheng.weixin.utils.HttpUtil;
import com.linhongzheng.weixin.utils.JSONUtil;
import com.linhongzheng.weixin.utils.URLConstants;

/**
 * Created by linhz on 2015/8/17.
 */
@Service("menuService")
public class MenuServiceImpl extends AbstractWeChatService implements
		IMenuService {
	private static Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);

	@Autowired
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
				String requestUrl = URLConstants.MENU.MENU_CREATE_URL.replace(
						"ACCESS_TOKEN", at);
				String jsonMenu = JSONUtil.objectToJson(getMenu(), "0");
				String jsonStr = HttpUtil.post(requestUrl, jsonMenu);

				System.out.println(jsonStr);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Menu getMenu() {

		ClickButton btn11 = new ClickButton();
		btn11.setName("今日歌曲");
		btn11.setType("click");
		btn11.setKey("V1001_TODAY_MUSIC");

		ClickButton btn12 = new ClickButton();
		btn12.setName("开源中国");
		btn12.setType("click");
		btn12.setKey("oschina");

		ClickButton btn13 = new ClickButton();
		btn13.setName("ITeye");
		btn13.setType("click");
		btn13.setKey("iteye");

		ViewButton btn2 = new ViewButton();
		btn2.setName("歌手简介");
		btn2.setType("view");
		btn2.setUrl("http://www.qq.com");

		ClickButton btn31 = new ClickButton();
		btn31.setName("Hello world");
		btn31.setType("click");
		btn31.setKey("V1001_HELLO_WORLD");

		ClickButton btn32 = new ClickButton();
		btn32.setName("赞一下我们");
		btn32.setType("click");
		btn32.setKey("V1001_GOOD");

		ComplexButton btn1 = new ComplexButton();
		btn1.setName("技术交流");
		btn1.setSub_button(new Button[] { btn11, btn12, btn13 });

		ComplexButton btn3 = new ComplexButton();
		btn3.setName("生活助手");
		btn3.setSub_button(new Button[] { btn31, btn32 });

		Menu menu = new Menu();
		menu.setButton(new Button[] { btn1, btn2, btn3 });

		return menu;
	}

	public IAccessTokenService getAccessTokenService() {
		return accessTokenService;
	}

	public void setAccessTokenService(IAccessTokenService accessTokenService) {
		this.accessTokenService = accessTokenService;
	}

}
