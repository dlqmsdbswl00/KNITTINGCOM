package com.oxtv.repository;

import com.oxtv.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserId(String userId);
    boolean existsByEmail(String email);
    boolean existsByUserId(String userId);
}