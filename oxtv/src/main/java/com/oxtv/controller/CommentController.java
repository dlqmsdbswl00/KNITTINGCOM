package com.oxtv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oxtv.model.Comment;
import com.oxtv.model.Post;
import com.oxtv.model.User;
import com.oxtv.service.CommentService;
import com.oxtv.service.PostService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/comments")
public class CommentController {

	private final CommentService commentService;
	private final PostService postService;

	public CommentController(CommentService commentService, PostService postService) {
		this.commentService = commentService;
		this.postService = postService;
	}

	@PostMapping("/create")
	public String createComment(@RequestParam Integer postId, @RequestParam String content, HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null)
			return "redirect:/login";

		Post post = postService.getPostById(postId).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

		// 댓글에 포함된 사용자 정보까지 초기화
		post.getComments().forEach(comment -> {
		    if (comment.getUser() != null) {
		        comment.getUser().getUserName(); // 강제 초기화
		    }
		});

		
		Comment comment = new Comment();
		comment.setPost(post);
		comment.setUser(loginUser);
		comment.setContent(content);

		commentService.saveComment(comment);
		return "redirect:/posts/" + postId;
	}
	
	@GetMapping("/{id}/edit")
	public String editCommentForm(@PathVariable Integer id, Model model, HttpSession session) {
	    Comment comment = commentService.findById(id);

	    User loginUser = (User) session.getAttribute("loginUser");
	    if (!comment.getUser().getUserId().equals(loginUser.getUserId())) {
	        throw new RuntimeException("수정 권한 없음");
	    }

	    model.addAttribute("comment", comment);
	    return "comment/editComment"; // JSP 경로
	}

}
