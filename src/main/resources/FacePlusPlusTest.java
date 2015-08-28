import junit.framework.TestCase;

import org.junit.Test;

import com.linhongzheng.weixin.utils.face.FacePlusPlusUtils;

public class FacePlusPlusTest extends TestCase {
	private static final String imageUrl = "http://www.faceplusplus.com.cn/wp-content"
			+ "/themes/faceplusplus/assets/img/demo/9.jpg";

	@Test
	public void testFaceDetect() {
		String faceJson = FacePlusPlusUtils.detectFace(imageUrl);
		System.out.println(FacePlusPlusUtils.parseFaceJson(faceJson));
	}

}
