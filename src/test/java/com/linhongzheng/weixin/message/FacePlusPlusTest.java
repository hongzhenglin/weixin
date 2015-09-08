package com.linhongzheng.weixin.message;
import junit.framework.TestCase;

import org.junit.Test;

import com.linhongzheng.weixin.utils.face.FacePlusPlusUtil;

public class FacePlusPlusTest extends TestCase {
	private static final String imageUrl = "http://www.faceplusplus.com.cn/wp-content"
			+ "/themes/faceplusplus/assets/img/demo/9.jpg";

	@Test
	public void testFaceDetect() {
		String faceJson = FacePlusPlusUtil.detectFace(imageUrl);
		System.out.println(FacePlusPlusUtil.parseFaceJson(faceJson));
	}

}
