package com.oxtv.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.oxtv.model.File;
import com.oxtv.model.Post;
import com.oxtv.repository.FileRepository;
import com.oxtv.repository.PostRepository;

import lombok.RequiredArgsConstructor;

import java.nio.file.StandardCopyOption;


@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final PostRepository postRepository;

    private final String uploadDir = "C:/Users/dlqms/hk-source/miniproject/oxtv/src/main/resources/static/uploads/"; 

    public void saveFiles(Integer postId, MultipartFile[] files) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글 없음"));

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    String originalName = file.getOriginalFilename();
                    String savedName = UUID.randomUUID().toString() + "_" + originalName;
                    Path path = Paths.get(uploadDir, savedName);
                    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                    File fileEntity = new File();
                    fileEntity.setPost(post);
                    fileEntity.setOriginalName(originalName);
                    fileEntity.setSavedName(savedName);
                    fileEntity.setUploadPath("/uploads/" + savedName); // 서버 경로 기준 URL
                    fileEntity.setUploadDate(LocalDateTime.now());

                    fileRepository.save(fileEntity);
                } catch (IOException e) {
                    throw new RuntimeException("파일 저장 실패", e);
                }
            }
        }
    }

    public List<File> getFilesByPostId(Integer postId) {
        return fileRepository.findByPostId(postId);
    }
}
