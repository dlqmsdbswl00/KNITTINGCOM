package com.oxtv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oxtv.model.File;

public interface FileRepository extends JpaRepository<File, Integer> {
	List<File> findByPostId(Integer postId);

}
