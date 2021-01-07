package com.cos.blog.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.blog.domain.user.User;
import com.cos.blog.domain.user.dto.JoinReqDto;
import com.cos.blog.domain.user.dto.LoginReqDto;
import com.cos.blog.service.UserService;
import com.cos.blog.util.Script;

// http://localhost:8000/blog/user
@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public UserController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	// http://localhost:8000/blog/user?cmd=값
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		UserService userService = new UserService();
		// http://localhost:8000/blog/user?cmd=loginForm
		if(cmd.equals("loginForm")) {
			// 아이디 기억 나중에 서비스를 만들 예정
			
			RequestDispatcher dis = request.getRequestDispatcher("user/LoginForm.jsp");
			dis.forward(request, response);
		} else if(cmd.equals("login")) {
			// 서비스 호출
			// 리플렉방식으로 하면 dto를 만들어서 들고 있음 리플렉션으로 필터링 해야함 하지만 이방식 사용안함.
			// 여기서 는 그냥 함
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			LoginReqDto dto = new LoginReqDto();
			dto.setUsername(username);
			dto.setPassword(password);
			// 서비스를 실행하기전에 데이터를 가공해야한다. 
			// 서비스에서 받은 데이터를 가지고 데이터베이스에 연결하든지 한다.
			User userEntity = userService.로그인(dto);
			if(userEntity != null) {
				HttpSession session = request.getSession();
				session.setAttribute("principal", userEntity);	// 인증주체
				RequestDispatcher dis = request.getRequestDispatcher("index.jsp");
				dis.forward(request, response);
			}else {
				Script.back(response, "로그인 실패");
			}
		} else if(cmd.equals("joinForm")) {
			RequestDispatcher dis = request.getRequestDispatcher("user/JoinForm.jsp");
			dis.forward(request, response);
		} else if(cmd.equals("join")) {
			// 리플렉션안하고 따로 뺴고싶으면 클래스를 만들어 처리함수를 생성하고 함수를 호출한다.
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			JoinReqDto dto = new JoinReqDto();
			dto.setUsername(username);
			dto.setPassword(password);
			dto.setEmail(email);
			dto.setAddress(address);
			dto.setUsername(username);
			dto.setPassword(password);
//			System.out.println("회원가입 : " + dto);
			int result = userService.회원가입(dto);
			if(result == 1) {
				RequestDispatcher dis = request.getRequestDispatcher("index.jsp");
				dis.forward(request, response);
			}else {
				// Script.back();
				Script.back(response, "회원가입 실패");
			}
		} else if(cmd.equals("usernameCheck")) {
			BufferedReader br = request.getReader();
			String username = br.readLine();
			System.out.println(username);
			int result = userService.유저네임중복체크(username);
			PrintWriter out = response.getWriter();
			if(result == 1) {
				out.print("ok");
			}else {
				out.print("fail");
			}
			out.flush();
		}else if(cmd.equals("logout")) {
			HttpSession session = request.getSession();
			session.invalidate();
			RequestDispatcher dis = request.getRequestDispatcher("index.jsp");
			dis.forward(request, response);
		}else if(cmd.equals("juso")) {
			RequestDispatcher dis = request.getRequestDispatcher("user/jusoPopup.jsp");
			dis.forward(request, response);
		}
		
	}
}
