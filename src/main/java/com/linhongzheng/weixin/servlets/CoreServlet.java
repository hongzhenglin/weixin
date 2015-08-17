package com.linhongzheng.weixin.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.linhongzheng.weixin.services.IWeChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linhongzheng.weixin.utils.SignUtil;

/**
 * Servlet implementation class CoreServlet
 */
@Singleton
@WebServlet("/CoreServlet")
public class CoreServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CoreServlet.class);
	/**
	 * Default constructor.
	 */
	public CoreServlet() {
	}

	@Inject
	IWeChatService weChatservice;

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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		response.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");

		String respMessage = weChatservice.processRequest(request);
		// 响应消息
		PrintWriter out = response.getWriter();
		out.print(respMessage);

		out.close();
	}


}
