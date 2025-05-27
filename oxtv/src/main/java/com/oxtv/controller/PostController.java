package com.oxtv.controller;

import com.oxtv.model.Post;
import com.oxtv.model.Role;
import com.oxtv.model.User;
import com.oxtv.service.PostService;
import com.oxtv.repository.UserRepository;
import jakarta.servlet.http.HttpSession;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;
	private final UserRepository userRepository;

	public PostController(PostService postService, UserRepository userRepository) {
		this.postService = postService;
		this.userRepository = userRepository;
	}

	@GetMapping
	public String listPosts(@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
		int pageSize = 10;
		Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id"));

		Page<Post> postsPage = (keyword == null || keyword.trim().isEmpty()) ? postService.getPostsPage(pageable)
				: postService.searchPosts(keyword, pageable);

		postsPage.getContent().forEach(post -> post.getUser().getNickname()); // Lazy 로딩 대비

		model.addAttribute("posts", postsPage.getContent());
		model.addAttribute("postsPage", postsPage); // Page<Post> 통째로 넘김
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", postsPage.getTotalPages());
		model.addAttribute("keyword", keyword);

		return "post/postlist";
	}

	@GetMapping("/new")
	public String showPostForm(HttpSession session, Model model) {
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login";
		}
		model.addAttribute("post", new Post());
		return "post/newpost";
	}

	@PostMapping("/new")
	public String createPost(@RequestParam String title, @RequestParam String content, @RequestParam String category,
			@SessionAttribute("loginUser") User loginUser) {
// 관리자 아니면 공지 못 쓰게 방어
		if (!loginUser.getRole().equals(Role.ADMIN) && category.equals("NOTICE")) {
			category = "FREE"; // 또는 에러 처리
		}

		Post post = new Post();
		post.setTitle(title);
		post.setContent(content);
		post.setCategory(category);
		post.setUser(loginUser);

		postService.createPost(post);
		return "redirect:/posts";
	}

	@GetMapping("/{id}")
	public String viewPost(@PathVariable Integer id, HttpSession session, Model model) {
		Post post = postService.getPostById(id).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser != null) {
			System.out.println("== 로그인 유저 ID: " + loginUser.getUserId());
			System.out.println("== 게시글 작성자 ID: " + post.getUser().getUserId());
		} else {
			System.out.println("== 로그인 유저 없음");
		}

		model.addAttribute("post", post);
		String formattedDate = post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm"));
		model.addAttribute("formattedCreatedAt", formattedDate);

		return "post/viewpost";
	}

	@GetMapping("/{id}/edit")
	public String editPostForm(@PathVariable Integer id, HttpSession session, Model model) {
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null) {
			// 로그인 안 됐으면 원래 요청 URL 저장
			session.setAttribute("redirectAfterLogin", "/posts/" + id + "/edit");
			return "redirect:/login";
		}

		Post post = postService.getPostById(id).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

		if (!post.getUser().getUserId().equals(loginUser.getUserId())) {
			// 권한 없으면 알림 띄우는 페이지나 목록으로 돌려보내고, 절대 수정 페이지로 안 넘어가도록
			// 예) 상세보기 페이지로 돌려보내면서 error 파라미터 줘서 alert 띄우게 함
			return "redirect:/posts/" + id + "?error=not_authorized";
		}

		model.addAttribute("post", post);
		return "post/editpost"; // 권한 있으면 수정 페이지로 감
	}

	// updatePost
	@PostMapping("/{id}/edit")
	public String updatePost(@PathVariable Integer id, @ModelAttribute Post post, HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null) {
			// 로그인 안 됐으면 원래 요청 URL 저장
			session.setAttribute("redirectAfterLogin", "/posts/" + id + "/edit");
			return "redirect:/login";
		}

		Post existingPost = postService.getPostById(id).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
		if (!existingPost.getUser().getUserId().equals(loginUser.getUserId())) {
			return "redirect:/posts/" + id + "?error=not_authorized";
		}

		post.setId(id);
		post.setUser(existingPost.getUser());
		postService.updatePost(post);
		return "redirect:/posts/" + id;
	}

	// deletePost
	@PostMapping("/{id}/delete")
	public String deletePost(@PathVariable Integer id, HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login";
		}

		Post existingPost = postService.getPostById(id).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
		if (!existingPost.getUser().getUserId().equals(loginUser.getUserId())) {
			return "redirect:/posts/" + id + "?error=not_authorized";
		}

		postService.deletePost(id);
		return "redirect:/posts";
	}
}