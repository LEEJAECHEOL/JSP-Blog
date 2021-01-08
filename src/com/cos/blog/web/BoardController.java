package com.cos.blog.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.blog.domain.board.Board;
import com.cos.blog.domain.board.dto.CommonRespDto;
import com.cos.blog.domain.board.dto.DeleteReqDto;
import com.cos.blog.domain.board.dto.DeleteRespDto;
import com.cos.blog.domain.board.dto.DetailRespDto;
import com.cos.blog.domain.board.dto.SaveReqDto;
import com.cos.blog.domain.board.dto.UpdateReqDto;
import com.cos.blog.domain.user.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.util.Script;
import com.google.gson.Gson;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BoardController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		BoardService boardService = new BoardService();
		// http://localhost:8000/blog/board?cmd=saveForm
		HttpSession session = request.getSession();
		if(cmd.equals("saveForm")) {
			User principal =(User)session.getAttribute("principal");
			if(principal != null) {
				RequestDispatcher dis = request.getRequestDispatcher("board/saveForm.jsp");
				dis.forward(request, response);
			}else {
				response.sendRedirect("user/LoginForm.jsp");
				RequestDispatcher dis = request.getRequestDispatcher("user/LoginForm.jsp");
				dis.forward(request, response);
			}
		}else if(cmd.equals("save")) {
//			int userId = Integer.parseInt(request.getParameter("userId"));
//			String title = request.getParameter("title");
//			String content = request.getParameter("content");
//			SaveReqDto dto = new SaveReqDto();
//			dto.setUserId(userId);
//			dto.setTitle(title);
//			dto.setContent(content);
			SaveReqDto dto = (SaveReqDto)request.getAttribute("dto");
			System.out.println(dto);
			int result = boardService.글쓰기(dto);
			if(result == 1) {
				 RequestDispatcher dis = request.getRequestDispatcher("index.jsp");
				 dis.forward(request, response);
			}else {
				Script.back(response, "글쓰기 실패");
			}
		}else if(cmd.equals("list")) {
			int page = Integer.parseInt(request.getParameter("page"));	// 최소: 0; next : 1
			List<Board> list = boardService.목록보기(page);
//			boolean isEnd = boardService.다음게시물목록여부(page + 1);
			int boardCount = boardService.글개수();
			int lastPage = (boardCount -1) / 3;
			double currentPercent = (double)page / lastPage * 100;
			RequestDispatcher dis = request.getRequestDispatcher("board/list.jsp");
			request.setAttribute("list", list);
//			request.setAttribute("isEnd", isEnd);
			request.setAttribute("lastPage", lastPage);
			request.setAttribute("currentPercent", currentPercent);
			dis.forward(request, response);
		}else if(cmd.equals("detail")) {
			int id = Integer.parseInt(request.getParameter("id"));
			
			DetailRespDto dto = boardService.글상세보기(id);	// board테이블 + user테이블 = 조인데이터
			if(dto == null) {
				Script.back(response, "상세보기 실패");
			}else {
				request.setAttribute("dto", dto);
				RequestDispatcher dis = request.getRequestDispatcher("board/detail.jsp");
				dis.forward(request, response);
			}
			
		}else if(cmd.equals("delete")) {
			User principal =(User)session.getAttribute("principal");
			BufferedReader br = request.getReader();
			String data = br.readLine();
			Gson gson = new Gson();
			DeleteReqDto dto = gson.fromJson(data, DeleteReqDto.class);
			if(!(principal != null && principal.getId() == dto.getUserId())) {
				PrintWriter out = response.getWriter();
				out.print("fail");
				out.flush();
				return ;
			}
			int result = boardService.게시글삭제(dto.getId());
			DeleteRespDto respDto = new DeleteRespDto();
			CommonRespDto<String> commonRespDto = new CommonRespDto<>();
			commonRespDto.setStatusCode(result);
			commonRespDto.setData("성공");
			String respData = gson.toJson(commonRespDto);
			PrintWriter out = response.getWriter();
			out.print(respData);
			out.flush();
		}else if(cmd.equals("updateForm")) {
			int id = Integer.parseInt(request.getParameter("id"));
			DetailRespDto dto = boardService.글상세보기(id);
			request.setAttribute("dto", dto);
			RequestDispatcher dis = request.getRequestDispatcher("board/updateForm.jsp");
			dis.forward(request, response);
		}else if(cmd.equals("update")) {
			int id = Integer.parseInt(request.getParameter("id"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			UpdateReqDto dto = new UpdateReqDto();
			dto.setId(id);
			dto.setTitle(title);
			dto.setContent(content);
			int result = boardService.글수정(dto);
			
			if(result == 1) {
				// requestDispatch를 사용하지 않는 이유는 url이 변경이 안됨.
				response.sendRedirect("board?cmd=detail&id="+id);
			}else {
				Script.back(response, "글수정 실패");
			}
			
		}
	}
}
