package com.linhongzheng.weixin.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.linhongzheng.weixin.services.IWeChatService;
import com.linhongzheng.weixin.utils.SignUtil;

/**
 * Servlet implementation class CoreServlet
 */
public class CoreServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CoreServlet.class);

	private IWeChatService weChatService;
	
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
				weChatService = (IWeChatService) ctx.getBean("weChatService");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	 
	/**
	 * Default constructor.
	 */
	public CoreServlet() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echoStr = request.getParameter("echostr");
		PrintWriter out = response.getWriter();
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echoStr);
		}
		out.close();
		out = null;
	}

	/**
	 * @throws UnsupportedEncodingException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		LOGGER.info("进入到CoreServlet doPost方法。");
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		// inj.getInstance(IWeChatService.class);

		String encryptType = request.getParameter("encrypt_type");
		PrintWriter out = null;
		try {
			out = response.getWriter();

			String respMessage = null;
			// 密文或兼容模式
			if (StringUtils.isNoneEmpty(encryptType)
					&& encryptType.equals("aes")) {
				// 处理密文
				respMessage = weChatService.processRequestCrypt(request);
			} else {// 明文模式

				respMessage = weChatService.processRequestRaw(request);

			}
			// 响应消息
			out.print(respMessage);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
				out = null;
			}
		}

	}
}
