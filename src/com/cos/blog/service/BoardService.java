package com.cos.blog.service;

import java.util.List;

import com.cos.blog.domain.board.Board;
import com.cos.blog.domain.board.BoardDao;
import com.cos.blog.domain.board.dto.DetailRespDto;
import com.cos.blog.domain.board.dto.SaveReqDto;

public class BoardService {
	
	private BoardDao boardDao;
	
	public BoardService() {
		this.boardDao = new BoardDao();
	}
	public int 글쓰기(SaveReqDto dto) {
		return boardDao.save(dto);
	}
	public List<Board> 목록보기(int page){
		return boardDao.findAll(page);
	}
	public boolean 다음게시물목록여부(int page) {
		return boardDao.isNextList(page);
	}
	public int 글개수() {
		return boardDao.count();
	}
	public DetailRespDto 글상세보기(int id){
		// 조회수 + 1
		int result = boardDao.updateReadCount(id);
		if(result == 1) {
			return boardDao.findById(id);
		}else {
			return null;
		}
	}
	public int 게시글삭제(int id) {
		return boardDao.deleteById(id);
	}
}
