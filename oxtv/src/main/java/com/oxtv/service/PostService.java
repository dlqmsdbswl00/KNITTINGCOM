package com.oxtv.service;

import com.oxtv.model.Category;
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

	public Page<Post> findNotices(String keyword, Pageable pageable) {
		if (keyword == null || keyword.isEmpty()) {
			return postRepository.findByCategory(Category.공지, pageable);
		} else {
			return postRepository.findByCategoryAndTitleContaining(Category.공지, keyword, pageable);
		}
	}


	public List<Post> getNoticePosts() {
		return postRepository.findByCategory(Category.공지);
	}

	public Page<Post> searchNoticePosts(String keyword, Pageable pageable) {
		return postRepository.findByCategoryAndTitleContainingIgnoreCase(Category.공지, keyword, pageable);
	}

	public Page<Post> findByCategory(Category category, Pageable pageable) {
	    return postRepository.findByCategory(category, pageable);
	}

	public Page<Post> findByCategoryAndTitleContaining(Category category, String keyword, Pageable pageable) {
	    return postRepository.findByCategoryAndTitleContaining(category, keyword, pageable);
	}


	public Page<Post> findByCategoryNotOrderByCreatedAtDesc(Category category, Pageable pageable) {
		return postRepository.findByCategoryNotOrderByCreatedAtDesc(category, pageable);
	}

	public Page<Post> searchPostsExcludeNotice(String keyword, String searchType, Pageable pageable) {
		switch (searchType) {
		case "nickname":
			return postRepository.findByUser_NicknameContainingIgnoreCaseAndCategoryNot(keyword, Category.공지, pageable);
		case "category":
			try {
				Category category = Category.valueOf(keyword);
				if (category == Category.공지) {
					return Page.empty(); // 공지 제외니까 빈 결과
				}
				return postRepository.findByCategory(category, pageable);
			} catch (IllegalArgumentException e) {
				return Page.empty(); // 잘못된 카테고리명
			}
		case "title":
		default:
			return postRepository.findByTitleContainingIgnoreCaseAndCategoryNot(keyword, Category.공지, pageable);
		}
	}

}
