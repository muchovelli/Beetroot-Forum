package com.beetrootforum.beetrootforum.repository;

import com.beetrootforum.beetrootforum.jpa.Role;
import com.beetrootforum.beetrootforum.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    void save(String username, String email, String publicKey, Set<Role> roles);
}
