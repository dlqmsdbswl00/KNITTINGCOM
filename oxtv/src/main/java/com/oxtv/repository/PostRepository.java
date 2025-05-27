package com.oxtv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oxtv.model.Post;
import com.oxtv.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
	List<Post> findByUser(User user);
	long countByUser(User user);

	// 최신순 페이징 조회 메서드 추가
	Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
