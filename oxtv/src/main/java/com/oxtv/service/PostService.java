package com.oxtv.service;

import com.oxtv.model.Post;
import com.oxtv.model.User;
import com.oxtv.repository.PostRepository;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {

	private final PostRepository postRepository;

	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public void createPost(Post post) {
		postRepository.save(post);
	}

	public List<Post> getAllPosts() {
		return postRepository.findAll();
	}

	public Optional<Post> getPostById(Integer id) {
		return postRepository.findById(id);
	}

	public void updatePost(Post post) {
		postRepository.save(post);
	}

	public void deletePost(Integer id) {
		postRepository.deleteById(id);
	}

	public List<Post> getPostsByUser(User user) {
		return postRepository.findByUser(user);
	}

	public Page<Post> getPostsPage(Pageable pageable) {
	    return postRepository.findAll(pageable);
	}

	public Page<Post> searchPosts(String keyword, Pageable pageable) {
	    return postRepository
	        .findByTitleContainingIgnoreCaseOrUser_NicknameContainingIgnoreCaseOrContentContainingIgnoreCase(
	            keyword, keyword, keyword, pageable);
	}

}
