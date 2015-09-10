package com.linhongzheng.weixin.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.linhongzheng.weixin.entity.sns.SNSUserInfo;
import com.linhongzheng.weixin.entity.sns.WeiXinOauth2Token;
import com.linhongzheng.weixin.services.IOauthService;
import com.linhongzheng.weixin.utils.CommonUtil;
import com.linhongzheng.weixin.utils.StringUtil;
import com.linhongzheng.weixin.utils.URLConstants;

public class OAuthServlet extends HttpServlet {
	
 
	private static final long serialVersionUID = -6417634377157263529L;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CoreServlet.class);

	private IOauthService oauthService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		ApplicationContext ctx = null;
		super.init(config);

		try {
			ctx = WebApplicationContextUtils.getWebApplicationContext(this
					.getServletContext());
		} catch (Exception e) {

		}
		if (ctx != null) {
			try {
				oauthService = (IOauthService) ctx.getBean("oauthService");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}



	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.info("进入到CoreServlet doGet方法。");

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String code = request.getParameter("code");
		if (StringUtil.isNotEmpty(code)) {
			WeiXinOauth2Token weiXinOauth2Token = oauthService
					.getOauth2AccessToken(code);
			String accessToken = weiXinOauth2Token.getAccessToken();
			String openId = weiXinOauth2Token.getOpenid();
			//如果网页授权作用域是snsapi_base，则不用执行下面的逻辑
			SNSUserInfo snsUserInfo = oauthService.getSNSUserInfo(accessToken,
					openId);
			request.setAttribute("snsUserInfo", snsUserInfo);
		}
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}


}
