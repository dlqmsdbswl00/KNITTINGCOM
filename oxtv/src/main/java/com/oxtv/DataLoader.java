package com.oxtv;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.oxtv.model.Category;
import com.oxtv.model.Comment;
import com.oxtv.model.Post;
import com.oxtv.model.Role;
import com.oxtv.model.User;
import com.oxtv.repository.CommentRepository;
import com.oxtv.repository.PostRepository;
import com.oxtv.repository.UserRepository;

import java.util.Optional;
import java.util.Random;

@Component
public class DataLoader implements CommandLineRunner {

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final CommentRepository commentRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public DataLoader(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository,
			BCryptPasswordEncoder passwordEncoder) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.commentRepository = commentRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) throws Exception {
		boolean resetPostsOnce = true; // ✅ 삭제 여부 플래그

		if (resetPostsOnce) {
			commentRepository.deleteAll(); // 댓글 먼저 삭제
			postRepository.deleteAll(); // 게시글 삭제
			System.out.println("💣 posts, comments 테이블 초기화 완료 (일회성).");
		}

		String testUserId = "test"; // 로그인용 ID
		Optional<User> existingUser = userRepository.findByUserId(testUserId);
		if (existingUser.isEmpty()) { // null 체크로 바꾸기
			User testUser = new User();
			testUser.setUserId(testUserId);
			testUser.setUserName("테스트이름");
			testUser.setNickname("테스트별명");
			testUser.setUserPassword(passwordEncoder.encode(testUserId)); // 암호화
			testUser.setEmail("testuser@example.com");
			testUser.setRole(Role.USER);
			System.out.println("email set: " + testUser.getEmail());

			userRepository.save(testUser);
			System.out.println("✅ testuser 계정 생성 완료 (ID: test / PW: test)");
		} else {
			System.out.println("ℹ️ testuser 계정 이미 존재함 (ID: test / PW: test)");
		}

		Optional<User> testUserOpt = userRepository.findByUserId("test");
		if (testUserOpt.isEmpty()) {
			System.out.println("test 유저가 없어서 게시글/댓글 생성 안 함.");
			return;
		}
		User testUser = testUserOpt.get();

		Optional<User> commenterOpt = userRepository.findByUserId("commenter");
		User commenter;
		if (commenterOpt.isEmpty()) {
			commenter = new User();
			commenter.setUserId("commenter");
			commenter.setUserName("댓글러");
			commenter.setNickname("댓글왕");
			commenter.setUserPassword(passwordEncoder.encode("commenter")); // 암호화
			commenter.setEmail("commenter@example.com");
			commenter.setRole(Role.USER);
			userRepository.save(commenter);
		} else {
			commenter = commenterOpt.get();
		}

		if (postRepository.countByUser(testUser) == 0) {
			String[] categories = { "자유", "질문", "도안" };
			Random random = new Random();

			for (int i = 1; i <= 30; i++) {
				Post post = new Post();
				post.setTitle("테스트 글 " + i);
				post.setContent("테스트 내용입니다. 게시물 번호 " + i);
				post.setUser(testUser);
				// 랜덤 카테고리 넣기
				String selected = categories[random.nextInt(categories.length)];
				post.setCategory(Category.valueOf(selected)); // ✅ enum으로 변환

				postRepository.save(post);

				for (int j = 1; j <= 3; j++) {
					Comment comment = new Comment();
					comment.setPost(post);
					comment.setUser(commenter);
					comment.setContent("댓글 " + j + " : 테스트 댓글 내용입니다.");
					commentRepository.save(comment);
				}
			}
			System.out.println("테스트 게시글 30개 및 댓글 90개 생성 완료.");
		} else {
			System.out.println("✅ 테스트 게시글 이미 있음. 추가 생성 안 함.");
		}

		// 관리자 공지사항 20개 생성 (공지 카테고리)
		Optional<User> adminOpt = userRepository.findByUserId("admin");
		if (adminOpt.isEmpty()) {
			System.out.println("관리자 계정이 없어서 공지사항 생성 안 함.");
			return;
		}
		User adminUser = adminOpt.get();

		long adminNoticeCount = postRepository.countByUserAndCategory(adminUser, Category.공지);
		if (adminNoticeCount == 0) {
			for (int i = 1; i <= 20; i++) {
				Post noticePost = new Post();
				noticePost.setTitle("공지사항 " + i);
				noticePost.setContent("관리자가 작성한 공지사항 내용입니다. 번호 " + i);
				noticePost.setUser(adminUser);
				noticePost.setCategory(Category.공지);
				postRepository.save(noticePost);

			}
			System.out.println("관리자 공지사항 20개 및 댓글 60개 생성 완료.");
		} else {
			System.out.println("✅ 관리자 공지사항 이미 있음. 추가 생성 안 함.");
		}
	}

}