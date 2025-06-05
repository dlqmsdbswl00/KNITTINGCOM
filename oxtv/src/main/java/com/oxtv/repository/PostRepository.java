package com.oxtv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oxtv.model.Category;
import com.oxtv.model.Post;
import com.oxtv.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
	List<Post> findByUser(User user);

	long countByUser(User user);

	// 최신순 페이징 조회 메서드 추가
	Page<Post> findByCategoryNotOrderByCreatedAtDesc(Category category, Pageable pageable);

	Page<Post> findByCategoryNotAndTitleContainingIgnoreCase(Category category, String keyword, Pageable pageable);

	Page<Post> findByTitleContainingIgnoreCaseOrUser_NicknameContainingIgnoreCaseOrContentContainingIgnoreCase(
			String title, String nickname, String content, Pageable pageable);

	// 공지사항 전체 조회
	List<Post> findByCategory(Category category);

	// 공지사항 중 키워드 포함 제목 조회
	Page<Post> findByCategory(Category category, Pageable pageable);

	Page<Post> findByCategoryAndTitleContaining(Category category, String keyword, Pageable pageable);

	// test
	long countByUserAndCategory(User user, Category category);

	Page<Post> findByTitleContainingIgnoreCaseAndCategoryNot(String keyword, Category excluded, Pageable pageable);

	Page<Post> findByUser_NicknameContainingIgnoreCaseAndCategoryNot(String nickname, Category excluded,
			Pageable pageable);
	
	Page<Post> findByCategoryAndTitleContainingIgnoreCase(Category category, String keyword, Pageable pageable);

}
