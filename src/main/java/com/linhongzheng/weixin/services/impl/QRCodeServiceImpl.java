package com.linhongzheng.weixin.services.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.linhongzheng.weixin.entity.WeiXinQRCode;
import com.linhongzheng.weixin.services.AbstractWeChatService;
import com.linhongzheng.weixin.services.ITokenService;
import com.linhongzheng.weixin.services.IQRCodeService;
import com.linhongzheng.weixin.utils.CommonUtil;
import com.linhongzheng.weixin.utils.URLConstants;

@Service("qrCodeService")
public class QRCodeServiceImpl extends AbstractWeChatService implements
		IQRCodeService {
	private static Logger log = LoggerFactory
			.getLogger(QRCodeServiceImpl.class);

	@Autowired
	ITokenService tokenService;

	/**
	 * 创建临时带参数二维码
	 * 
	 * @param expireSeconds
	 *            该二维码有效时间，以秒为单位。 最大不超过604800（即7天）
	 * @param sceneId
	 *            场景值ID，临时二维码时为32位非0整型
	 */
	@Override
	public WeiXinQRCode createTemporaryQRCode(int expireSeconds, int sceneId) {
		String at = null;
		try {
			at = tokenService.getAccessToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
		WeiXinQRCode weiXinQRCode = null;
		if (null != at) {
			String requestUrl = URLConstants.QRCODE.TEMPORARAY_QRCODE_URL
					.replace("ACCESS_TOKEN", at);
			String jsonData = "{\"expire_seconds\":%d,\"action_name\":\"QR_SCENE\",\"action_info\":{\"scene\":{\"scene_id\":%d}}}";
			String jsonResp = CommonUtil.httpsRequest(requestUrl, "POST",
					String.format(jsonData, expireSeconds, sceneId));
			if (jsonResp != null) {
				JSONObject jsonObject = JSON.parseObject(jsonResp);
				int errcode = jsonObject.getIntValue("errcode");
				if (errcode != 0) {
					log.error("创建临时带参数二维码失败：" + jsonObject.getString("errmsg"));
				} else {
					weiXinQRCode = new WeiXinQRCode();
					weiXinQRCode.setExpireSeconds(jsonObject
							.getIntValue("expire_seconds"));
					weiXinQRCode.setTicket(jsonObject.getString("ticket"));
					weiXinQRCode.setUrl(jsonObject.getString("url"));
					log.error("创建临时带参数二维码成功：" + weiXinQRCode.toString());
				}
			}
		}
		return weiXinQRCode;
	}

	/**
	 * 创建永久带参数二维码
	 * 
	 * @param sceneId
	 *            永久二维码时最大值为100000（目前参数只支持1--100000）
	 */
	@Override
	public WeiXinQRCode createPermanentQRCode(int sceneId) {
		String at = null;
		try {
			at = tokenService.getAccessToken();
		} catch (Exception e) {

			e.printStackTrace();
		}
		WeiXinQRCode weiXinQRCode = null;
		if (null != at) {
			String requestUrl = URLConstants.QRCODE.PERMANENT_QRCODE_URL
					.replace("ACCESS_TOKEN", at);
			String jsonData = "{\"action_name\":\"QR_LIMIT_SCENE\",\"action_info\":{\"scene\":{\"scene_id\":%d}}}";
			String jsonResp = CommonUtil.httpsRequest(requestUrl, "POST",
					String.format(jsonData, sceneId));
			if (jsonResp != null) {
				JSONObject jsonObject = JSON.parseObject(jsonResp);
				int errcode = jsonObject.getIntValue("errcode");
				if (errcode != 0) {
					log.error("创建永久带参数二维码失败：" + jsonObject.getString("errmsg"));
				} else {
					weiXinQRCode = new WeiXinQRCode();
					weiXinQRCode.setExpireSeconds(jsonObject
							.getIntValue("expire_seconds"));
					weiXinQRCode.setTicket(jsonObject.getString("ticket"));
					weiXinQRCode.setUrl(jsonObject.getString("url"));
					log.error("创建永久带参数二维码成功：" + weiXinQRCode.toString());
				}
			}
		}
		return weiXinQRCode;

	}

	@Override
	public String getQRCode(String ticket, String savePath) {

		String filePath = null;

		String requestUrl = URLConstants.QRCODE.GET_QRCODE_URL.replace(
				"TICKET", CommonUtil.urlEncodeUTF8(ticket));
		try {
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			if (!savePath.endsWith("/")) {
				savePath += "/";
			}
			// 将ticket作为文件名
			filePath = savePath + ticket + ".jpg";
			BufferedInputStream bis = new BufferedInputStream(
					conn.getInputStream());
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}
			fos.close();
			bis.close();

			conn.disconnect();
			log.info("根据ticket换取二维码成功,filePath=" + filePath);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("根据ticket换取二维码失败" + e);
		}
		return filePath;
	}

	public ITokenService getTokenService() {
		return tokenService;
	}

	public void setTokenService(ITokenService tokenService) {
		this.tokenService = tokenService;
	}

}
