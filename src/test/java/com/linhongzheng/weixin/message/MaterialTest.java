package com.linhongzheng.weixin.message;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.linhongzheng.weixin.services.ISourceMaterialService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class MaterialTest extends AbstractJUnit4SpringContextTests {
	@Resource
	ISourceMaterialService sourceMaterialService;

	@Test
	public void testUploadTemp() {
		String medialUrl = "http://linhzweixintest.sinaapp.com/image/2048/demo.jpg";

		System.out.println(sourceMaterialService
				.uploadMedia("image", medialUrl).toString());
	}

	@Test
	public void testDownloadTemp() {

		sourceMaterialService
				.getMedia(
						"RKP94pIQN_KvpJ52zUYfGBMPLP4Ec-Yjg0vsqvSAgCsYBeUvvzlDU2hh77h-MCJ1",
						"C:/");
	}

}
