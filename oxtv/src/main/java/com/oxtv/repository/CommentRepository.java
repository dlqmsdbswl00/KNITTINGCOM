package com.oxtv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oxtv.model.Comment;
import com.oxtv.model.Post;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPostIdOrderByCreatedAtAsc(Integer postId);
    
    //test
    long countByPost(Post post);

}
