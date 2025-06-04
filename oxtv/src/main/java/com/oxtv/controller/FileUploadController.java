package com.oxtv.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oxtv.model.File;
import com.oxtv.service.FileService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class FileUploadController {
	private final FileService fileService;

	public FileUploadController(FileService fileService) {
		this.fileService = fileService;
	}

	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam("postId") Integer postId, @RequestParam("files") MultipartFile[] files,
			RedirectAttributes redirectAttributes) {
		fileService.saveFiles(postId, files);
		redirectAttributes.addFlashAttribute("message", "파일 업로드 성공!");
		return "redirect:/posts/" + postId;
	}
	
	@GetMapping("/download")
	public void downloadFile(@RequestParam("fileId") Integer fileId, HttpServletResponse response) throws IOException {
	    File file = fileService.getFileById(fileId)
	            .orElseThrow(() -> new IllegalArgumentException("파일 없음"));

	    java.io.File savedFile = new java.io.File(file.getSavedPath());

	    if (!savedFile.exists()) {
	        throw new FileNotFoundException("물리 파일이 없음");
	    }

	    response.setContentType("application/octet-stream");
	    response.setHeader("Content-Disposition",
	            "attachment; filename=\"" + URLEncoder.encode(file.getOriginalName(), "UTF-8") + "\"");
	    response.setContentLength((int) savedFile.length());
	    
	    try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(savedFile));
	            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream())) {
	           byte[] buffer = new byte[1024];
	           int len;
	           while ((len = in.read(buffer)) != -1) {
	               out.write(buffer, 0, len);
	           }
	       }
	    try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(savedFile));
	         BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream())) {
	        byte[] buffer = new byte[1024];
	        int len;
	        while ((len = in.read(buffer)) != -1) {
	            out.write(buffer, 0, len);
	        }
	    }
	}

}
