package com.cos.blog.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.blog.domain.board.dto.SaveReqDto;

public class XssConfig implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		String gubun = req.getParameter("cmd");
		String context = req.getContextPath();
		String uri = req.getRequestURI();
		String endPoint = uri.replace(context, "");
		gubun = gubun == null ? "" :gubun;
		if(gubun.equals("save") && endPoint.equals("/board")) {
			int userId = Integer.parseInt(request.getParameter("userId"));
			String title = req.getParameter("title");
			title = title.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			String content = req.getParameter("content");
			SaveReqDto dto = new SaveReqDto();
			dto.setUserId(userId);
			dto.setTitle(title);
			dto.setContent(content);
			req.setAttribute("dto", dto);
		}
		chain.doFilter(req, res);	

	}

}
