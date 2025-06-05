package com.oxtv.repository;

import com.oxtv.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUserId(String userId);
    boolean existsByEmail(String email);
    boolean existsByUserId(String userId);
    boolean existsByNickname(String nickname);

}