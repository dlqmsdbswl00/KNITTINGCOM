package com.mini.Knit.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.mini.Knit.command.DelBoardCommand;
import com.mini.Knit.command.InsertBoardCommand;
import com.mini.Knit.command.UpdateBoardCommand;
import com.mini.Knit.dtos.BoardDto;
import com.mini.Knit.dtos.FileBoardDto;
import com.mini.Knit.service.BoardService;
import com.mini.Knit.service.FileService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	@Autowired
	private FileService fileService;

	@GetMapping(value = "/boardList")
	public String boardList(Model model) {
		System.out.println("글목록 보기");

		List<BoardDto> list = boardService.getAllList();
		model.addAttribute("list", list);
		model.addAttribute("delBoardCommand", new DelBoardCommand());
		return "board/boardList";// forward 기능, "redirect:board/boardList"
	}

	@GetMapping(value = "/boardInsert")
	public String boardInsertForm(Model model) {
		model.addAttribute("insertBoardCommand", new InsertBoardCommand());
		return "board/boardInsertForm";
	}

	@PostMapping(value = "/boardInsert")
	public String boardInsert(@Validated InsertBoardCommand insertBoardCommand, BindingResult result,
			@RequestParam("filename") MultipartFile file, HttpServletRequest request, Model model)
			throws IllegalStateException, IOException, ServletException {
		if (result.hasErrors()) {
			System.out.println("글을 모두 입력하세요");
			return "board/boardInsertForm";
		}

// BoardService에 파일과 함께 게시글 추가 요청
		boardService.insertBoard(insertBoardCommand, file, request);

		return "redirect:/board/boardList";
	}

	// 상세보기
	@GetMapping(value = "/boardDetail")
	public String boardDetail(int board_seq, Model model) {
		BoardDto dto = boardService.getBoard(board_seq);

		// 유효값처리용
		model.addAttribute("updateBoardCommand", new UpdateBoardCommand());
		// 출력용
		model.addAttribute("dto", dto);
		System.out.println(dto);
		return "board/boardDetail";
	}

	// 수정하기
	@PostMapping(value = "/boardUpdate")
	public String boardUpdate(@Validated UpdateBoardCommand updateBoardCommand, BindingResult result, Model model) {

		if (result.hasErrors()) {
			System.out.println("수정내용을 모두 입력하세요");
			// 코드 추가--------------------------------------------
			BoardDto dto = boardService.getBoard(updateBoardCommand.getBoard_seq());
			model.addAttribute("dto", dto);
			// --------------------------------------------------
			return "board/boardDetail";
		}

		boardService.updateBoard(updateBoardCommand);

		return "redirect:/board/boardDetail?board_seq=" + updateBoardCommand.getBoard_seq();
	}

	@GetMapping(value = "/download")
	public void download(int file_seq, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException, IOException {

		FileBoardDto fdto = fileService.getFileInfo(file_seq);// 파일정보가져오기

		fileService.fileDownload(fdto.getOrigin_filename(), fdto.getStored_filename(), request, response);
	}

	@RequestMapping(value = "mulDel", method = { RequestMethod.POST, RequestMethod.GET })
	public String mulDel(@Validated DelBoardCommand delBoardCommand, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.out.println("최소하나 체크하기");
			List<BoardDto> list = boardService.getAllList();
			model.addAttribute("list", list);
			return "board/boardlist";
		}
		boardService.mulDel(delBoardCommand.getSeq());
		System.out.println("글삭제함");
		return "redirect:/board/boardList";
	}
}