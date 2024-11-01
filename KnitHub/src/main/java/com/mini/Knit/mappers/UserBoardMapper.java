package com.mini.Knit.mappers;

import com.mini.Knit.dtos.UserBoardDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserBoardMapper {
    
    // 사용자 추가 메소드
    public int addUser(com.mini.Knit.dtos.UserBoardDTO dto);
    
    // 사용자 ID 중복 체크 메소드
    public String idChk(String username);
    
    // 사용자 로그인 메소드
    public com.mini.Knit.dtos.UserBoardDTO loginUser(String username);
}