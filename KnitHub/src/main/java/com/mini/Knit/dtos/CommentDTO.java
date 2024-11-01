package com.mini.Knit.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

@Data // getter, setter, toString 등을 자동으로 생성
@NoArgsConstructor // 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 포함하는 생성자 생성
@Alias(value = "commentDTO") // 별칭을 commentDTO로 설정

public class CommentDTO {
    private int commentId;
    private int boardId; // Foreign Key
    private int userId; // Foreign Key
    private String content;
    private Date createdAt;
    private Date updatedAt;

    // Getters and Setters
}
