package com.linhongzheng.weixin.utils;

public class URLConstants {
	public static final String MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
	public static final String UPLOADNEWS_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=";
	public static final String MASS_SENDALL_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=";
	public static final String MASS_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=";
	public static final String MASS_DELETE_URL = "https://api.weixin.qq.com//cgi-bin/message/mass/delete?access_token=";
	public static final String TEMPLATE_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
	public static final String PAYFEEDBACK_URL = "https://api.weixin.qq.com/payfeedback/update";
	public static final String DEFAULT_HANDLER = "com.gson.inf.DefaultMessageProcessingHandlerImpl";
	public static final String GET_MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=";
	public static final String UPLOAD_MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=";
	public static final String JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=";
	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	public static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 百度音乐搜索地址
	public static String BAIDU_MUSIC_URL = "http://box.zhangmen.baidu.com/x?op=12&count=1&title={TITLE}$${AUTHOR}";
}
