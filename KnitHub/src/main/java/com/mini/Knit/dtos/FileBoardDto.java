package com.mini.Knit.dtos;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data // getter, setter, toString 등을 자동으로 생성
@NoArgsConstructor // 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 포함하는 생성자 생성
@Alias(value = "fileBoardDto")


public class FileBoardDto {
	private int file_seq;
	private int board_seq;
	private String origin_filename;
	private String stored_filename;
}
