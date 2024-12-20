package com.mini.Knit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.mini.Knit.command.AddUserCommand;
import com.mini.Knit.command.LoginCommand;
import com.mini.Knit.dtos.MemberDto;
import com.mini.Knit.mapper.MemberMapper;
import com.mini.Knit.status.RoleStatus;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

//@RequiredArgsConstructor ;lombok 기능: final 필드를 초기화 - Autowired 생략가능
@Service
public class MemberService {
	
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public boolean addUser(AddUserCommand addUserCommand) {
		
		MemberDto mdto=new MemberDto();
		mdto.setId(addUserCommand.getId());
		mdto.setName(addUserCommand.getName());
		
		//password암호화하여 저장하자
		mdto.setPassword(passwordEncoder.encode(addUserCommand.getPassword()));
		
		mdto.setEmail(addUserCommand.getEmail());
		mdto.setRole(RoleStatus.USER+"");//등급추가
		return memberMapper.addUser(mdto);
	}
	
	public String idChk(String id) {
		return memberMapper.idChk(id);
	}
	
	public String login(LoginCommand loginCommand
			           ,HttpServletRequest request
			           ,Model model) {
		MemberDto dto = memberMapper.loginUser(loginCommand.getId());
		String path="redirect:/home";
		if(dto!=null) {
			//로그인 폼에서 입력받은 패스워드값과 DB에 암호화된 패스워드 비교
			if(passwordEncoder.matches(loginCommand.getPassword()
					                  , dto.getPassword())) {
				System.out.println("패스워드 같음: 회원이 맞음");
				//session객체에 로그인 정보 저장
				request.getSession().setAttribute("user", dto);
				return path;
			}else {
				System.out.println("패스워드 틀림");
				model.addAttribute("msg", "패스워드를 확인하세요");
				path="member/login";
			}
		}else {
			System.out.println("회원이 아닙니다. ");
			model.addAttribute("msg", "아이디를 확인하세요");
			path="member/login";
		}
		
		return path;
	}
}