package com.linhongzheng.weixin.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.linhongzheng.weixin.utils.CommonUtil;

public class BrowerFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		if (CommonUtil.isMicroMessenger(request)) {
			chain.doFilter(req, resp);
		} else {
			HttpServletResponse response = (HttpServletResponse) resp;
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write("请使用微信浏览器访问!");
			out.close();
		}
	}

	@Override
	public void destroy() {

	}

}
