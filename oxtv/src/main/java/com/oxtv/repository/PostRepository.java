package com.oxtv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oxtv.model.Post;
import com.oxtv.model.User;

import java.util.List; 

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);
}
