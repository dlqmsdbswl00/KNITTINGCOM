package com.mini.Knit.dtos;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getter, setter, toString 등을 자동으로 생성
@NoArgsConstructor // 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 포함하는 생성자 생성
@Alias(value = "boardDTO") // 별칭을 boardDTO로 설정

public class BoardDTO {
	private int boardId;
	private int userId; // Foreign Key
	private String title;
	private String content;
	private String category;
	private String filePath;
	private String fileType;
	private Date createdAt;
	private Date updatedAt;

	// Getters and Setters
}
