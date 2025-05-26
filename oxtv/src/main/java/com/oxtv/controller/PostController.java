package com.oxtv.controller;

import com.oxtv.model.Post;
import com.oxtv.model.User;
import com.oxtv.service.PostService;
import com.oxtv.repository.UserRepository;
import jakarta.servlet.http.HttpSession;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	public String listPosts(Model model) {
	    List<Post> posts = postService.getAllPosts();

	    List<Map<String, Object>> postsWithFormattedDate = posts.stream().map(post -> {
	        Map<String, Object> map = new HashMap<>();
	        map.put("id", post.getId());
	        map.put("title", post.getTitle());
	        map.put("userName", post.getUser().getUserName());
	        // 날짜를 문자열로 포맷
	        map.put("formattedCreatedAt", post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm")));
	        return map;
	    }).collect(Collectors.toList());

	    model.addAttribute("posts", postsWithFormattedDate);
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
	public String createPost(@ModelAttribute Post post, HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login";
		}

		post.setUser(loginUser);
		postService.createPost(post);
		return "redirect:/posts";
	}

	@GetMapping("/{id}")
	public String viewPost(@PathVariable Integer id, Model model) {
	    Post post = postService.getPostById(id)
	                 .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

	    model.addAttribute("post", post);

	    // LocalDateTime -> String 포맷해서 같이 넣기
	    String formattedDate = post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm"));
	    model.addAttribute("formattedCreatedAt", formattedDate);

	    return "post/viewpost";
	}


	@GetMapping("/{id}/edit")
	public String editPostForm(@PathVariable Integer id, Model model) {
		Post post = postService.getPostById(id).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
		model.addAttribute("post", post);
		return "post/editpost";
	}

	// updatePost
	@PostMapping("/{id}/edit")
	public String updatePost(@PathVariable Integer id, @ModelAttribute Post post, HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null) {
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