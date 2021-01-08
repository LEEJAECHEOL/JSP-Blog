package com.cos.blog.domain.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cos.blog.config.DB;
import com.cos.blog.domain.board.dto.DetailRespDto;
import com.cos.blog.domain.board.dto.SaveReqDto;
import com.cos.blog.domain.user.User;

public class BoardDao {
	public int save(SaveReqDto dto) { // 회원가입
		String sql = "INSERT INTO board(userId, title, content, createDate) VALUES(?, ?, ?, now())";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getUserId());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {	// 항상 실행
			DB.close(conn, pstmt);
		}
		return -1;
	}
	public List<Board> findAll(int page){
		List<Board> list = new ArrayList<>();
		String sql = "SELECT id, userId, title, readCount, createDate FROM board ORDER BY id DESC LIMIT ?, 3";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page * 3);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(Board.builder()
							.id(rs.getInt("id"))
							.userId(rs.getInt("userId"))
							.title(rs.getString("title"))
							.readCount(rs.getInt("readCount"))
							.createDate(rs.getTimestamp("createDate"))
							.build()
						);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {	// 항상 실행
			DB.close(conn, pstmt, rs);
		}
		return null;
	}
	public boolean isNextList(int page) {
		String sql = "SELECT * FROM board ORDER BY id DESC LIMIT ?, 3";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page * 3);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return false;
			}else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {	// 항상 실행
			DB.close(conn, pstmt, rs);
		}
		return false;
	}
	public int count() {
		String sql = "SELECT COUNT(*) FROM board";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {	// 항상 실행
			DB.close(conn, pstmt, rs);
		}
		return -1;
	}
	public DetailRespDto findById(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT b.id, b.title,b.content, b.readCount, u.username ");
		sb.append("FROM board b INNER JOIN users u ");
		sb.append("ON b.userId = u.id ");
		sb.append("WHERE b.id = ?");
		String sql = sb.toString();
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				DetailRespDto dto = new DetailRespDto();
				dto.setId(rs.getInt("b.id"));
				dto.setTitle(rs.getString("b.title"));
				dto.setContent(rs.getString("b.content"));
				dto.setReadCount(rs.getInt("b.readCount"));
				dto.setUsername(rs.getString("u.username"));
				return dto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {	// 항상 실행
			DB.close(conn, pstmt, rs);
		}
		return null;
	}
	public void updateReadCount(int id) {
		String sql = "UPDATE board SET readCount = readCount + 1 WHERE id = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {	// 항상 실행
			DB.close(conn, pstmt);
		}
	}
}
