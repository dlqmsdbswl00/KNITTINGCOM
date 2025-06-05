package com.oxtv.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oxtv.model.Post;
import com.oxtv.model.Role;
import com.oxtv.model.User;
import com.oxtv.service.PostService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
public class AdminController {
	@Autowired
	private PostService postService;

	@GetMapping("/admin/exportPosts")
	public void exportPostsToExcel(HttpServletResponse response, HttpSession session) throws IOException {
	    // 관리자 인증 체크 (필수)
	    User loginUser = (User) session.getAttribute("loginUser");
	    if (loginUser == null || !loginUser.getRole().equals(Role.ADMIN)) {
	        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	        return;
	    }

	    List<Post> posts = postService.getAllPosts(); // DB에서 글 목록 불러오기

	    Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Posts");

	    // 헤더
	    Row header = sheet.createRow(0);
	    header.createCell(0).setCellValue("글 번호");
	    header.createCell(1).setCellValue("제목");
	    header.createCell(2).setCellValue("작성자");
	    header.createCell(3).setCellValue("작성일");

	    int rowNum = 1;
	    for (Post post : posts) {
	        Row row = sheet.createRow(rowNum);
	        row.createCell(0).setCellValue(rowNum);
	        row.createCell(1).setCellValue(post.getTitle());
	        row.createCell(2).setCellValue(post.getUser().getNickname());
	        row.createCell(3).setCellValue(post.getFormattedCreatedAt());
	        rowNum++;
	    }

	    String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	    String filename = "OXTV_글목록_" + dateStr + ".xlsx";
	    String encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");

	    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFilename);

	    workbook.write(response.getOutputStream());
	    workbook.close();
	}

}
