package com.cos.blog.web;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.blog.domain.board.dto.CommonRespDto;
import com.cos.blog.domain.reply.dto.DeleteReqDto;
import com.cos.blog.domain.reply.Reply;
import com.cos.blog.domain.reply.dto.SaveReqDto;
import com.cos.blog.service.ReplyService;
import com.cos.blog.util.Script;
import com.google.gson.Gson;

@WebServlet("/reply")
public class ReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReplyController() {
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
		ReplyService replyService = new ReplyService();
		// http://localhost:8000/blog/board?cmd=saveForm
		HttpSession session = request.getSession();
		if(cmd.equals("save")) {
			BufferedReader br = new BufferedReader(request.getReader());
			String reqData = br.readLine();
			Gson gson = new Gson();
			SaveReqDto dto = gson.fromJson(reqData, SaveReqDto.class);
			int result = replyService.댓글저장(dto);
			Reply reply = null;
			CommonRespDto<Reply> commonRespDto = new CommonRespDto<>();
			if(result != -1) {
				reply = replyService.댓글찾기(result);
//				System.out.println("reply : " +reply.toString());
				commonRespDto.setStatusCode(1);
				commonRespDto.setData(reply);
			}else {
				commonRespDto.setStatusCode(-1);
			}
			String responseData = gson.toJson(commonRespDto);
			Script.responseData(response, responseData);
		}else if(cmd.equals("delete")) {
			System.out.println("run");
			BufferedReader br = new BufferedReader(request.getReader());
			String reqData = br.readLine();
			System.out.println(reqData);
			Gson gson = new Gson();
			DeleteReqDto dto = gson.fromJson(reqData, DeleteReqDto.class);
			System.out.println("댓글삭제 : " +dto);
			int result = replyService.댓글삭제(dto);
			CommonRespDto<Reply> commonRespDto = new CommonRespDto<>();
			if(result == 1) {
				commonRespDto.setStatusCode(1);
			}else {
				commonRespDto.setStatusCode(-1);
			}
			String responseData = gson.toJson(commonRespDto);
			Script.responseData(response, responseData);
		}
	}
}
