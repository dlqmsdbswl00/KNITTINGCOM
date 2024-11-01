package com.mini.Knit.service;

import com.mini.Knit.dtos.UserBoardDTO;
import com.mini.Knit.mappers.UserBoardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class UserBoardService {
	private final UserBoardMapper userBoardMapper;
	private static final Logger logger = LoggerFactory.getLogger(UserBoardService.class);

	@Autowired
    private DataSource dataSource;
	
	@Autowired
	public UserBoardService(UserBoardMapper userBoardMapper) {
		this.userBoardMapper = userBoardMapper;
	}

	// 데이터베이스 연결 확인 메서드
    public void checkDatabaseConnection() {
        try (Connection connection = dataSource.getConnection()) {
            logger.info("Database connected successfully: " + connection.getMetaData().getURL());
        } catch (SQLException e) {
            logger.error("Database connection failed", e);
        }
    }
    
	// 회원가입 메소드
	public boolean registerUser(UserBoardDTO userBoardDTO) {
	    try {
	        // 회원가입 로직
	        return true;
	    } catch (Exception e) {
	        logger.error("회원가입 중 오류 발생", e);
	        return false;
	    }
	}


	// ID 중복 체크 메소드
	public String checkUsername(String username) {
		return userBoardMapper.idChk(username);
	}

	// 로그인 메소드
	public UserBoardDTO login(String username) {
		return userBoardMapper.loginUser(username);
	}
}
