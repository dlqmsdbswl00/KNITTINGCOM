package com.oxtv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oxtv.service.FileService;

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
}
