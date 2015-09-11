package com.linhongzheng.weixin.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.linhongzheng.weixin.entity.menu.Button;
import com.linhongzheng.weixin.entity.menu.ClickButton;
import com.linhongzheng.weixin.entity.menu.ComplexButton;
import com.linhongzheng.weixin.entity.menu.Menu;
import com.linhongzheng.weixin.entity.menu.ViewButton;
import com.linhongzheng.weixin.services.AbstractWeChatService;
import com.linhongzheng.weixin.services.IAccessTokenService;
import com.linhongzheng.weixin.services.IMenuService;
import com.linhongzheng.weixin.utils.CommonUtil;
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
	 * @see com.linhongzheng.weixin.services.IMenuService#creatMenu()
	 */
	@Override
	public void creatMenu() {

		try {
			String at = accessTokenService.getAccessToken();
			if (null != at) {
				String requestUrl = URLConstants.MENU.MENU_CREATE_URL.replace(
						"ACCESS_TOKEN", at);
				String jsonMenu = JSONUtil.objectToJson(getMenu(), "0");

				String jsonStr = CommonUtil.httpsRequest(requestUrl, "POST",
						jsonMenu);
				if (jsonStr != null) {
					JSONObject jsonObject = JSON.parseObject(jsonStr);
					int errcode = jsonObject.getIntValue("errcode");
					if (errcode != 0) {
						log.error(jsonObject.getString("errmsg"));
					} else {
						log.info(jsonStr);
					}
				}

			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	public void creatMenu(String jsonMenu) {
		try {
			String at = accessTokenService.getAccessToken();
			if (null != at) {
				String requestUrl = URLConstants.MENU.MENU_CREATE_URL.replace(
						"ACCESS_TOKEN", at);

				String jsonStr = CommonUtil.httpsRequest(requestUrl, "POST",
						jsonMenu);
				if (jsonStr != null) {
					JSONObject jsonObject = JSON.parseObject(jsonStr);
					int errcode = jsonObject.getIntValue("errcode");
					if (errcode != 0) {
						log.error(jsonObject.getString("errmsg"));
					} else {
						log.info(jsonStr);
					}
				}

			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	public String queryMenu() {

		String jsonStr = null;
		try {
			String at = accessTokenService.getAccessToken();
			if (null != at) {
				String requestUrl = URLConstants.MENU.MENU_GET_URL.replace(
						"ACCESS_TOKEN", at);
				jsonStr = CommonUtil.httpsRequest(requestUrl, "GET", "");
				if (jsonStr != null) {
					JSONObject jsonObject = JSON.parseObject(jsonStr);
					int errcode = jsonObject.getIntValue("errcode");
					if (errcode != 0) {
						log.error(jsonObject.getString("errmsg"));
					} else {
						log.info(jsonStr);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	@Override
	public String deleteMenu() {
		String jsonStr = null;
		try {
			String at = accessTokenService.getAccessToken();
			if (null != at) {
				String requestUrl = URLConstants.MENU.MENU_DELETE_URL.replace(
						"ACCESS_TOKEN", at);
				jsonStr = CommonUtil.httpsRequest(requestUrl, "GET", "");
				if (jsonStr != null) {
					JSONObject jsonObject = JSON.parseObject(jsonStr);
					int errcode = jsonObject.getIntValue("errcode");
					if (errcode != 0) {
						log.error(jsonObject.getString("errmsg"));
					} else {
						log.info(jsonStr);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	private static Menu getMenu() {

		ViewButton btn11 = new ViewButton();
		btn11.setName("CocoaChina");
		btn11.setType("view");
		btn11.setUrl("http://www.iteye.com");

		ClickButton btn12 = new ClickButton();
		btn12.setName("开源中国");
		btn12.setType("click");
		btn12.setKey("oschina");

		ClickButton btn13 = new ClickButton();
		btn13.setName("ITeye");
		btn13.setType("click");
		btn13.setKey("iteye");

		ViewButton btn21 = new ViewButton();
		btn21.setName("京东");
		btn21.setType("view");
		btn21.setUrl("http://m.jd.com");

		ViewButton btn22 = new ViewButton();
		btn22.setName("淘宝");
		btn22.setType("view");
		btn22.setUrl("http://m.taobao.com");

		ViewButton btn23 = new ViewButton();
		btn23.setName("唯品会");
		btn23.setType("view");
		btn23.setUrl("http://m.vipshop.com");

		ViewButton btn24 = new ViewButton();
		btn24.setName("当当网");
		btn24.setType("view");
		btn24.setUrl("http://m.dangdang.com");

		ViewButton btn25 = new ViewButton();
		btn25.setName("苏宁易趣");
		btn25.setType("view");
		btn25.setUrl("http://m.suning.com");

		ViewButton btn31 = new ViewButton();
		btn31.setName("多泡");
		btn31.setType("view");
		btn31.setUrl("http://www.duopao.com");

		ViewButton btn32 = new ViewButton();
		btn32.setName("一窝88");
		btn32.setType("view");
		btn32.setUrl("http://yi588.com");

		ViewButton btn33 = new ViewButton();
		btn33.setName("OAuth2.0测试");
		btn33.setType("view");
		btn33.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx16c644d1873b9073&redirect_uri=http%3A%2F%2F1.linhzweixintest.sinaapp.com%2FoauthServlet&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");

		ComplexButton btn1 = new ComplexButton();
		btn1.setName("技术交流");
		btn1.setSub_button(new Button[] { btn11, btn12, btn13 });

		ComplexButton btn2 = new ComplexButton();
		btn2.setName("购物");
		btn2.setSub_button(new Button[] { btn21, btn22, btn23, btn24, btn25 });

		ComplexButton btn3 = new ComplexButton();
		btn3.setName("网页游戏");
		btn3.setSub_button(new Button[] { btn31, btn32, btn33 });

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
