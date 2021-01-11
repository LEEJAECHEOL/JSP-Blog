package com.cos.blog.domain.reply;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.cos.blog.config.DB;
import com.cos.blog.domain.reply.dto.SaveReqDto;

public class ReplyDao {
	public Reply findById(int id) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT r.*, u.username ");
		sb.append("FROM reply r INNER JOIN users u ");
		sb.append("ON r.userId = u.id ");
		sb.append("WHERE r.id = ?");
		String sql = sb.toString();
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Reply reply = new Reply();
				reply.setId(rs.getInt("r.id"));
				reply.setUserId(rs.getInt("r.userId"));
				reply.setBoardId(rs.getInt("r.boardId"));
				reply.setContent(rs.getString("r.content"));
				reply.setCreateDate(rs.getTimestamp("r.createDate"));
				reply.setUsername(rs.getString("u.username"));
				return reply;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {	// 항상 실행
			DB.close(conn, pstmt, rs);
		}
		return null;
	}
	public int save(SaveReqDto dto) {
		String sql = "INSERT INTO reply(userId, boardId, content, createDate) VALUES(?, ?, ?, now())";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int generateKey;
		try {
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, dto.getUserId());
			pstmt.setInt(2, dto.getBoardId());
			pstmt.setString(3, dto.getContent());
			int result = pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				generateKey = rs.getInt(1);
				if(result == 1) {
					return generateKey;
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {	// 항상 실행
			DB.close(conn, pstmt);
		}
		return -1;
	}
}
