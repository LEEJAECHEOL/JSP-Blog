package com.cos.blog.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class ForbiddenUrlConfig implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		System.out.println("실행됨");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('잘못된 접근입니다.');");
		out.println("location.href='/blog/board?cmd=list';");
		out.println("</script>");
		out.flush();
		
	}

}
