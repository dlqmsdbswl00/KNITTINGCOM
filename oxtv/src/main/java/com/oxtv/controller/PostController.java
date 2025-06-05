package com.oxtv.controller;

import com.oxtv.model.Category;
import com.oxtv.model.File;
import com.oxtv.model.Post;
import com.oxtv.model.Role;
import com.oxtv.model.User;
import com.oxtv.service.FileService;
import com.oxtv.service.PostService;
import com.oxtv.util.FnUtils;
import com.oxtv.repository.UserRepository;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.servlet.http.HttpSession;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;
	private final UserRepository userRepository;
	private final FileService fileService;

	public PostController(PostService postService, UserRepository userRepository, FileService fileService) {
		this.postService = postService;
		this.userRepository = userRepository;
		this.fileService = fileService;
	}

	@Autowired
	private HttpSession session;

	private boolean isAdmin() {
		User loginUser = (User) session.getAttribute("loginUser");
		return loginUser != null && loginUser.getRole() == Role.ADMIN;
	}

	@GetMapping
	public String listPosts(@RequestParam(required = false) String keyword,
			@RequestParam(required = false, defaultValue = "title") String searchType,
	        @RequestParam(defaultValue = "0") int page, HttpSession session, Model model) {
		int pageSize = 10;
	
		Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
		Page<Post> postsPage = postService.findByCategory(Category.공지, pageable);

		if (keyword == null || keyword.trim().isEmpty()) {
			
		    // 공지 제외 일반 게시글 목록
	        postsPage = postService.findByCategoryNotOrderByCreatedAtDesc(Category.공지, pageable);
		} else {
		    // 키워드로 검색된 일반 게시글 목록 (공지 제외 조건도 넣어야 함)
	        postsPage = postService.searchPostsExcludeNotice(keyword.trim(), searchType, pageable);
		}
;

		postsPage.getContent().forEach(post -> post.getUser().getNickname()); // Lazy 로딩 대비

		model.addAttribute("posts", postsPage.getContent());
		model.addAttribute("postsPage", postsPage); // Page<Post> 통째로 넘김
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", postsPage.getTotalPages());
		model.addAttribute("keyword", keyword);

		User loginUser = (User) session.getAttribute("loginUser");
		boolean isAdmin = loginUser != null && loginUser.getRole() == Role.ADMIN;
		model.addAttribute("isAdmin", isAdmin);

		return "post/postlist";
	}

	@GetMapping("/new")
	public String showPostForm(HttpSession session, Model model) {
		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "redirect:/login";
		}
		model.addAttribute("post", new Post());
		model.addAttribute("loginUser", loginUser);

		boolean isAdmin = loginUser.getRole() == Role.ADMIN;
		model.addAttribute("isAdmin", isAdmin);

		model.addAttribute("roleClassName", loginUser.getRole().getClass().getName());
		model.addAttribute("roleName", loginUser.getRole().name());

		return "post/newpost";
	}

	@PostMapping("/new")
	public String createPost(@RequestParam String title, @RequestParam String content, @RequestParam String category,
			@RequestParam("files") MultipartFile[] files, @SessionAttribute("loginUser") User loginUser) {

		Category categoryEnum = Category.valueOf(category);

		if (loginUser.getRole() == Role.USER && categoryEnum == Category.공지) {
			throw new IllegalArgumentException("일반 유저는 공지 작성 불가");
		}

		if (loginUser.getRole() == Role.ADMIN && categoryEnum != Category.공지) {
			throw new IllegalArgumentException("관리자는 공지만 작성 가능");
		}

		// 게시글 작성
		Post post = new Post();
		post.setTitle(title);
		post.setContent(content);
		post.setCategory(categoryEnum);
		post.setUser(loginUser);

		postService.createPost(post);

		if (files != null && files.length > 0) {
			fileService.saveFiles(post.getId(), files); // 파일 저장
		}

		if (post.getCategory() == Category.공지) {
			return "redirect:/posts/notice"; // ← 공지면 공지 목록으로
		} else {
			return "redirect:/posts"; // ← 일반글이면 일반 게시판
		}
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

		List<File> fileList = fileService.getFilesByPostId(id);

		model.addAttribute("post", post);
		model.addAttribute("fileList", fileList);

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

		List<File> fileList = fileService.getFilesByPostId(id);
		model.addAttribute("fileList", fileList);

		model.addAttribute("post", post);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("isAdmin", loginUser.getRole() == Role.ADMIN);
		model.addAttribute("roleName", loginUser.getRole().name());
		model.addAttribute("roleClassName", loginUser.getRole().getClass().getName());

		return "post/editpost"; // 권한 있으면 수정 페이지로 감
	}

	// updatePost
	@PostMapping("/{id}/edit")
	public String updatePost(@PathVariable Integer id, @ModelAttribute Post post,
			@RequestParam(value = "files", required = false) MultipartFile[] files,
			@RequestParam(value = "deleteFileIds", required = false) List<Integer> deleteFileIds, HttpSession session) {

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

		// 관리자: 공지만 가능 / 일반유저: 공지 못 씀
		if (loginUser.getRole() == Role.ADMIN && post.getCategory() != Category.공지) {
			throw new IllegalArgumentException("관리자는 공지만 작성/수정 가능");
		} else if (loginUser.getRole() == Role.USER && post.getCategory() == Category.공지) {
			throw new IllegalArgumentException("일반 유저는 공지 작성/수정 불가");
		}

		// 기존 게시글 업데이트
		existingPost.setTitle(post.getTitle());
		existingPost.setContent(post.getContent());
		existingPost.setCategory(post.getCategory());

		// 삭제할 파일 처리
		if (deleteFileIds != null) {
			for (Integer fileId : deleteFileIds) {
				fileService.deleteFile(fileId);
			}
		}

		if (files != null && files.length > 0) {
			fileService.saveFiles(existingPost.getId(), files); // 기존 저장로직 재활용
		}
		postService.updatePost(existingPost);

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

	// 관리자 기능
	@GetMapping("/notice")
	public String showNoticePosts(@RequestParam(required = false) String keyword,
	                              @RequestParam(defaultValue = "0") int page,
	                              Model model, HttpSession session) {

	    Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
	    Page<Post> postsPage;

	    if (keyword == null || keyword.trim().isEmpty()) {
	        postsPage = postService.findByCategory(Category.공지, pageable);
	    } else {
	        postsPage = postService.findByCategoryAndTitleContaining(Category.공지, keyword.trim(), pageable);
	    }

	    model.addAttribute("postsPage", postsPage);
	    model.addAttribute("isAdmin", FnUtils.isAdmin(session));
	    model.addAttribute("keyword", keyword);
	    return "post/notice";
	}


	@PostMapping("/delete")
	@ResponseBody
	public Map<String, Object> deletePosts(@RequestParam List<Integer> postIds, HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		Map<String, Object> response = new HashMap<>();

		if (loginUser == null) {
			response.put("success", false);
			response.put("message", "로그인이 필요합니다.");
			return response;
		}

		try {
			for (Integer postId : postIds) {
				Post existingPost = postService.getPostById(postId)
						.orElseThrow(() -> new IllegalArgumentException("게시글 없음: " + postId));

				// 작성자 아니면 건너뜀 (선택 삭제니까 권한 없으면 무시해도 됨)
				if (!existingPost.getUser().getUserId().equals(loginUser.getUserId())) {
					continue;
				}

				postService.deletePost(postId);
			}
			response.put("success", true);
			response.put("message", "선택한 게시글을 삭제했습니다.");
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "삭제 중 오류 발생: " + e.getMessage());
		}
		return response;
	}

}