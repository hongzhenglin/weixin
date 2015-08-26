package com.linhongzheng.weixin.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linhongzheng.weixin.services.IWeChatService;
import com.linhongzheng.weixin.services.impl.WeChatServiceImpl;
import com.linhongzheng.weixin.utils.SignUtil;

/**
 * Servlet implementation class CoreServlet
 */
public class CoreServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CoreServlet.class);
	// @Inject
	// private Injector inj;
	IWeChatService weChatservice;

	/**
	 * Default constructor.
	 */
	public CoreServlet() {
		weChatservice = new WeChatServiceImpl();
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.info("进入到doPost方法。");
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		response.setCharacterEncoding("UTF-8");
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
					respMessage = weChatservice.processRequestCrypt(request);
				} else {// 明文模式
					
					respMessage = weChatservice.processRequestRaw(request);
				 
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
