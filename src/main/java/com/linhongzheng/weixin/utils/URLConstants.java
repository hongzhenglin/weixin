package com.linhongzheng.weixin.utils;

public class URLConstants {

	// 获取token接口(GET)
	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	// GET方式请求获得jsapi_ticket
	public static final String JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=ACCESS_TOKEN";

	// 获取微信服务器IP地址列表，请求方式: GET
	public static final String GET_WEIXINIP_URL = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";

	public static final String GET_SHORT_URL = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";

	public static class QRCODE {
		// 创建临时二维码ticket
		public static final String TEMPORARAY_QRCODE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
		// 创建永久二维码ticket
		public static final String PERMANENT_QRCODE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
		// 通过ticket换取二维码
		public static final String GET_QRCODE_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
	}

	public static class USER {
		// 创建分组
		public final static String CREATE_GROUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";
		// 查询所有分组 GET
		public final static String GET_GROUPS_URL = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
		// 查询用户所在分组
		public final static String GETID_USERGROUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=ACCESS_TOKEN";
		// 修改分组名
		public final static String UPDATE_GROUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";
		// 移动用户分组
		public final static String UPDATE_USERGROUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";
		// 批量移动用户分组
		public final static String BATUPADATE_USERGRUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate?access_token=ACCESS_TOKEN";
		// 删除分组
		public final static String DELETE_GROUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/delete?access_token=ACCESS_TOKEN";
		// 设置用户备注名
		public final static String UPDATE_USERREMARK_URL = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=ACCESS_TOKEN";

		// 获取用户基本信息（包括UnionID机制） GET
		public final static String GET_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

		// 批量获取用户基本信息
		public final static String BATCHGET_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";

		// 获取用户列表 GET
		public final static String BATCHGET_USER_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	}

	public static class GROUP {
		// 根据分组进行群发
		public final static String GROUP_MESSAGE_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
		// 根据OpenID列表群发
		public final static String OPENID_MESSAGE_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
		// 删除群发
		public final static String DELETE_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token=ACCESS_TOKEN";
		// 查询群发消息发送状态
		public final static String MESSAGE_STATUS_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/get?access_token=ACCESS_TOKEN";
	}

	public static class OAUTH {
		// 获取CODE
		public final static String OAUTH2_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
		// oauth2授权接口(GET)
		public final static String OAUTH2_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APPSECRET&code=CODE&grant_type=authorization_code";

		// 刷新access_token接口（GET）
		public final static String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";

		// 拉取用户信息 GET
		public final static String USERINFO_TOKEN_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	}

	public static class CUSTOM {
		// 增加客服接口
		public final static String ADD_CUSTOMER_URL = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN";
		// 更新客服接口
		public final static String UPDATE_CUSTOMER_URL = "https://api.weixin.qq.com/customservice/kfaccount/update?access_token=ACCESS_TOKEN";

		public final static String DEL_CUSTOMER_URL = "https://api.weixin.qq.com/customservice/kfaccount/del?access_token=ACCESS_TOKEN";

		public final static String LIST_CUSTOMER_URL = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=ACCESS_TOKEN";
		// 设置客服头像 POST/FORM
		public final static String UPLOAD_HEADIMAGE_URL = "http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT";

		public final static String SEND_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

	}

	/**
	 * 自定义菜单
	 */
	public static class MENU {

		// 创建菜单URL POST
		public static final String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
		// 查询菜单URL GET
		public static final String MENU_GET_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
		// 删除菜单URL GET
		public static final String MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	}

	public static class BAIDU {
		// 百度翻译地址
		public static final String BAIDU_TRANSLATE_URL = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id={AK}&q={keyWord}&from=auto&to=auto";

		// 百度音乐搜索地址
		public static final String BAIDU_MUSIC_URL = "http://box.zhangmen.baidu.com/x?op=12&count=1&title={TITLE}$${AUTHOR}";

		// 百度天气预报
		public static final String BAIDU_WEATHER_URL = "http://api.map.baidu.com/telematics/v3/weather?location={LOCATION}&output=json&ak={AK}";

	}

	public static class TEMPLATE {

		public static final String SET_INDUSTRY_URL = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";

		public static final String GET_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN";
		// 发送模板消息
		public static final String TEMPLATE_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

	}

	public static class MATERIAL {
		// POST/FORM 新增临时素材
		public static final String UPLOAD_TEMP_MEDIA_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
		// GET 获取临时素材
		public static final String GET_TEMP_MEDIA_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

		// 新增永久素材
		public static final String ADD_NEW_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=ACCESS_TOKEN";

	}

	/**
	 * 微信支付接口地址
	 */
	// 微信支付统一接口(POST)
	public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	// 微信退款接口(POST)
	public final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	// 订单查询接口(POST)
	public final static String CHECK_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	// 关闭订单接口(POST)
	public final static String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	// 退款查询接口(POST)
	public final static String CHECK_REFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	// 对账单接口(POST)
	public final static String DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
	// 短链接转换接口(POST)
	public final static String SHORT_URL = "https://api.mch.weixin.qq.com/tools/shorturl";
	// 接口调用上报接口(POST)
	public final static String REPORT_URL = "https://api.mch.weixin.qq.com/payitil/report";

	public static final String UPLOADNEWS_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
	public static final String MASS_SENDALL_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
	public static final String MASS_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
	public static final String MASS_DELETE_URL = "https://api.weixin.qq.com//cgi-bin/message/mass/delete?access_token=ACCESS_TOKEN";

	public static final String PAYFEEDBACK_URL = "https://api.weixin.qq.com/payfeedback/update";

}
