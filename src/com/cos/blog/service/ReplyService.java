package com.cos.blog.service;

import com.cos.blog.domain.reply.dto.DeleteReqDto;

import java.util.List;

import com.cos.blog.domain.reply.Reply;
import com.cos.blog.domain.reply.ReplyDao;
import com.cos.blog.domain.reply.dto.SaveReqDto;

public class ReplyService {
	
	private ReplyDao replyDao;
	
	public ReplyService() {
		this.replyDao = new ReplyDao();
	}
	
	public int 댓글저장(SaveReqDto dto) {
		return replyDao.save(dto);
	}
	public Reply 댓글찾기(int id) {
		return replyDao.findById(id);
	}
	public int 댓글삭제(DeleteReqDto dto) {
		return replyDao.DeleteById(dto);
	}
	public List<Reply> 글목록보기(int boardId){
		return replyDao.findAll(boardId);
	}
}
