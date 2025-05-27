package com.oxtv.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.oxtv.model.Comment;
import com.oxtv.model.Post;
import com.oxtv.model.User;
import com.oxtv.repository.CommentRepository;
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
	@ResponseBody
	public ResponseEntity<?> createComment(@RequestParam Integer postId, @RequestParam String content,
			HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");

		Post post = postService.getPostById(postId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글 없음"));

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

		return ResponseEntity.ok("댓글 작성 성공");
	}

	@PostMapping("/{id}/edit")
	@ResponseBody
	public ResponseEntity<String> editComment(@PathVariable Integer id, @RequestParam String content,
			HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		Comment comment = commentService.findById(id);

		if (loginUser == null) {
			Integer postId = comment.getPost().getId();
			session.setAttribute("redirectAfterLogin", "/posts/" + postId);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
		}

		if (!comment.getUser().getId().equals(loginUser.getId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한 없음");
		}

		comment.setContent(content);
		commentService.saveComment(comment);
		return ResponseEntity.ok("ok");
	}

	@PostMapping("/{id}/delete")
	@ResponseBody
	public ResponseEntity<String> deleteComment(@PathVariable Integer id, HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");

		Comment comment = commentService.findById(id);
		if (!comment.getUser().getId().equals(loginUser.getId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한 없음");
		}

		commentService.deleteComment(id);
		return ResponseEntity.ok("ok");
	}

}
