package com.mini.Knit.dtos;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.mini.Knit.dtos.FileBoardDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getter, setter, toString 등을 자동으로 생성
@NoArgsConstructor // 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 포함하는 생성자 생성
@Alias(value = "boardDto") // 별칭을 boardDTO로 설정

public class BoardDto {
	private int board_seq;
	private String id;
	private String title;
	private String content;
	private Date regdate;
	private String delflag;
	
	//Join용 맴버필드
	private List<FileBoardDto> fileBoardDto;
}
