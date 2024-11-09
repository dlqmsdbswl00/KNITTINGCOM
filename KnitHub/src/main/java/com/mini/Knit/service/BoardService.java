package com.mini.Knit.service;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mini.Knit.command.InsertBoardCommand;
import com.mini.Knit.command.UpdateBoardCommand;
import com.mini.Knit.dtos.BoardDto;
import com.mini.Knit.dtos.FileBoardDto;
import com.mini.Knit.mapper.BoardMapper;
import com.mini.Knit.mapper.FileMapper;
import com.mini.Knit.service.FileService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class BoardService {

	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private FileMapper fileMapper;
	@Autowired
	private FileService fileService;

	// 글목록 조회
	public List<BoardDto> getAllList() {
		return boardMapper.getAllList();
	}

	// 글 추가, 파일업로드및 파일정보 추가
	@Transactional(rollbackFor = Exception.class)
	public void insertBoard(InsertBoardCommand insertBoardCommand, MultipartFile file, HttpServletRequest request)
			throws IllegalStateException, IOException {
		// command:UI -> dto:DB 데이터 옮겨담기
		BoardDto boardDto = new BoardDto();
		boardDto.setId(insertBoardCommand.getId());
		boardDto.setTitle(insertBoardCommand.getTitle());
		boardDto.setContent(insertBoardCommand.getContent());

	    // 새 게시글 추가
	    boardMapper.insertBoard(boardDto);
	    
		// 새글을 추가할때 파라미터로 전달된 boardDto객체에 자동으로,
		// 증가된 board_seq값이 저장
		boardMapper.insertBoard(boardDto);// 새글 추가
		System.out.println("파일첨부여부: " + (file.isEmpty() ? "없음" : "있음"));

		// 첨부된 파일들이 있는 경우
		if (!file.isEmpty()) {
			// 파일 저장 경로 설정: 상대 경로로 설정
			String uploadDir = request.getServletContext().getRealPath("/uploads/");
			System.out.println("파일저장경로: " + uploadDir);

			// 파일 이름 처리
			String originalFilename = file.getOriginalFilename();
			String storedFilename = System.currentTimeMillis() + "_" + originalFilename; // 중복 방지용 파일 이름 생성

			// 파일 저장
			File uploadedFile = new File(uploadDir, storedFilename);
			file.transferTo(uploadedFile);

			// 파일 정보 DB에 추가
			FileBoardDto fileBoardDto = new FileBoardDto();
			fileBoardDto.setBoard_seq(boardDto.getBoard_seq()); // 새로 생성된 게시글의 board_seq
			fileBoardDto.setOrigin_filename(originalFilename);
			fileBoardDto.setStored_filename(storedFilename);

			fileMapper.insertFileBoard(fileBoardDto); // 파일 정보 DB에 추가
		}
	}


	// 상세내용조회
	public BoardDto getBoard(int board_seq) {
		return boardMapper.getBoard(board_seq);
	}

	// 수정하기
	public boolean updateBoard(UpdateBoardCommand updateBoardCommand) {
		// command:UI ---> DTO:DB
		BoardDto dto = new BoardDto();
		dto.setBoard_seq(updateBoardCommand.getBoard_seq());
		dto.setTitle(updateBoardCommand.getTitle());
		dto.setContent(updateBoardCommand.getContent());
		return boardMapper.updateBoard(dto);
	}

	public boolean mulDel(String[] seqs) {
		return boardMapper.mulDel(seqs);
	}
}