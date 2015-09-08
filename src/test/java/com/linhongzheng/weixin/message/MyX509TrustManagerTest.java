package com.linhongzheng.weixin.message;

import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.linhongzheng.weixin.utils.MyX509TrustManager;

public class MyX509TrustManagerTest {
	public static void main(String[] args) throws Exception {
		// 创建SSLContext对象，并使用我们指定的信任管理器初始化
		TrustManager[] tm = { new MyX509TrustManager() };
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, tm, new java.security.SecureRandom());
		// 从上述SSLContext对象中得到SSLSocketFactory对象
		SSLSocketFactory ssf = sslContext.getSocketFactory();
		// 创建URL对象
		URL myURL = new URL(
				"https://api.weixin.qq.com/cgi-bin/menu/create?access_token=rLzBtDXhuzc5BUQ50RfSrZ_4ez9zzjcD6rLPZEfB7H34ItyYV_uPJRijQNnU9x3WAErqE-LF-mBs1KybnGrhG1EaQhkl5Yty_vOEd-GSSWQ");
		// 创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
		HttpsURLConnection httpsConn = (HttpsURLConnection) myURL
				.openConnection();
		httpsConn.setSSLSocketFactory(ssf);
		// 取得该连接的输入流，以读取响应内容
		InputStreamReader insr = new InputStreamReader(
				httpsConn.getInputStream());
		// 读取服务器的响应内容并显示
		int respInt = insr.read();
		while (respInt != -1) {
			System.out.print((char) respInt);
			respInt = insr.read();
		}

	}
}
