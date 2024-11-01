package com.mini.Knit.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data // getter, setter, toString 등을 자동으로 생성
@NoArgsConstructor // 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 포함하는 생성자 생성
@Alias(value = "userBoardDTO") // 별칭을 userBoardDTO로 설정

public class UserBoardDTO {
    
    private int userId;          // 사용자 ID
    private String username;      // 사용자 이름
    private String password;      // 비밀번호
    private String email;         // 이메일
    private String name;          // 이름
    private String role;          // 사용자 역할
    private String createdAt;     // 생성 일자
    private String updatedAt;     // 수정 일자
}
