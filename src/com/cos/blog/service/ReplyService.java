package com.cos.blog.service;

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
}
