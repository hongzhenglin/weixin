package com.linhongzheng.weixin.message;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.linhongzheng.weixin.entity.WeiXinQRCode;
import com.linhongzheng.weixin.services.IQRCodeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class QRCodeTest extends AbstractJUnit4SpringContextTests {
	@Resource
	IQRCodeService qrCodeService;

	@Test
	public void testCreateTempQRCode() {
		qrCodeService.createTemporaryQRCode(120, 123);
	}

	@Test
	public void testCreatePermanentQRCode() {
		WeiXinQRCode qrCode = qrCodeService.createPermanentQRCode(456);
		qrCodeService.getQRCode(qrCode.getTicket(), "C:/");
	}

	@Test
	public void testGetQRCode() {

	}
}
