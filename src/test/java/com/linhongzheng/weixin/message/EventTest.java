package com.linhongzheng.weixin.message;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.linhongzheng.weixin.entity.menu.Button;
import com.linhongzheng.weixin.entity.menu.ClickButton;
import com.linhongzheng.weixin.entity.menu.ComplexButton;
import com.linhongzheng.weixin.entity.menu.Menu;
import com.linhongzheng.weixin.services.IMenuService;
import com.linhongzheng.weixin.utils.JSONUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class EventTest extends AbstractJUnit4SpringContextTests {

	@Resource
	IMenuService menuService;

	@Test
	public void testCreateEventMenu() {
		menuService.creatMenu(createMenu());
	}

	private String createMenu() {
		ClickButton btn11 = new ClickButton();
		btn11.setName("扫描推事件");
		btn11.setType("scancode_push");
		btn11.setKey("KEY_11");

		ClickButton btn12 = new ClickButton();
		btn12.setName("扫描带提示");
		btn12.setType("scancode_waitmsg");
		btn12.setKey("KEY_12");

		ClickButton btn21 = new ClickButton();
		btn21.setName("系统拍照发图");
		btn21.setType("pic_sysphoto");
		btn21.setKey("KEY_21");

		ClickButton btn22 = new ClickButton();
		btn22.setName("拍照或者相册发图");
		btn22.setType("pic_photo_or_album");
		btn22.setKey("KEY＿22");

		ClickButton btn23 = new ClickButton();
		btn23.setName("微信相册发图");
		btn23.setType("pic_weixin");
		btn23.setKey("KEY_23");

		ClickButton btn31 = new ClickButton();
		btn31.setName("发送位置");
		btn31.setType("location_select");
		btn31.setKey("KEY_31");

		ComplexButton btn1 = new ComplexButton();
		btn1.setName("扫码");
		btn1.setSub_button(new Button[] { btn11, btn12 });

		ComplexButton btn2 = new ComplexButton();
		btn2.setName("发图");
		btn2.setSub_button(new Button[] { btn21, btn22, btn23 });

		Menu menu = new Menu();
		menu.setButton(new Button[] { btn1, btn2, btn31 });

		return JSONUtil.objectToJson(menu, "0");

	}

}
