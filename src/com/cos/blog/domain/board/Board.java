package com.cos.blog.domain.board;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Board {
	private int id;
	private int userId;
	private String title;
	private String content;
	private int readCount;	// 조회수
	private Timestamp createDate;
}
