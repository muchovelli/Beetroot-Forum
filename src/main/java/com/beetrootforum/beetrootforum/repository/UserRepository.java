package com.beetrootforum.beetrootforum.repository;

import com.beetrootforum.beetrootforum.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Optional<User> findByEmail(String email);
}
