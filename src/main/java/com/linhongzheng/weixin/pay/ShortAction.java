package com.linhongzheng.weixin.pay;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import ch.qos.logback.core.joran.spi.XMLUtil;

import com.linhongzheng.weixin.utils.CommonUtil;
import com.linhongzheng.weixin.utils.ConfigUtil;
import com.linhongzheng.weixin.utils.PayCommonUtil;
import com.linhongzheng.weixin.utils.URLConstants;

/**
 * 
 * @author 李欣桦
 * @date 2015-1-6下午5:14:01
 * 
 *       将静态native支付链接转为短链接
 */
public class ShortAction {
	public static void main(String[] args) throws Exception {
		SortedMap<Object, Object> paras = new TreeMap<Object, Object>();
		paras.put("appid", ConfigUtil.APPID);
		paras.put("mch_id", ConfigUtil.MCH_ID);
		paras.put("time_stamp", Long.toString(new Date().getTime()));
		paras.put("nonce_str", PayCommonUtil.CreateNoncestr());
		paras.put("product_id", "No.201401051607001");// 商品号要唯一
		String sign = PayCommonUtil.createSign("UTF-8", paras);
		paras.put("sign", sign);
		String url = "weixin://wxpay/bizpayurl?sign=SIGN&appid=APPID&mch_id=MCHID&product_id=PRODUCTID&time_stamp=TIMESTAMP&nonce_str=NOCESTR";
		String nativeUrl = url.replace("SIGN", sign)
				.replace("APPID", ConfigUtil.APPID)
				.replace("MCHID", ConfigUtil.MCH_ID)
				.replace("PRODUCTID", (String) paras.get("product_id"))
				.replace("TIMESTAMP", (String) paras.get("time_stamp"))
				.replace("NOCESTR", (String) paras.get("nonce_str"));
		System.out.println("nativeUrl=" + nativeUrl);

		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", ConfigUtil.APPID);
		parameters.put("mch_id", ConfigUtil.MCH_ID);
		parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
		parameters.put("long_url", CommonUtil.urlEncodeUTF8(nativeUrl));
		String sign2 = PayCommonUtil.createSign("UTF-8", parameters);
		parameters.put("sign", sign2);
		String requestXML = PayCommonUtil.getRequestXml(parameters);
		String result = CommonUtil.httpsRequest(URLConstants.SHORT_URL, "POST",
				requestXML);
		System.out.println(result);
		Map<String, String> map = XMLUtil.doXMLParse(result);
		String returnCode = map.get("return_code");
		String resultCode = map.get("result_code");
		if (returnCode.equalsIgnoreCase("SUCCESS")
				&& resultCode.equalsIgnoreCase("SUCCESS")) {
			String shortUrl = map.get("short_url");
			// TODO 拿到shortUrl，写代码生成二维码
			System.out.println("shortUrl=" + shortUrl);
		}
	}
}
