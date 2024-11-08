package com.mini.Knit.mapper;

import com.mini.Knit.dtos.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    
    // 사용자 추가 메소드
    public boolean addUser(MemberDto dto);
    
    // 사용자 ID 중복 체크 메소드
    public String idChk(String id);
    
    // 사용자 로그인 메소드
    public MemberDto loginUser(String id);
}