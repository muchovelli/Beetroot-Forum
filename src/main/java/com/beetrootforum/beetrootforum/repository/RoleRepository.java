package com.beetrootforum.beetrootforum.repository;

import com.beetrootforum.beetrootforum.enums.RoleName;
import com.beetrootforum.beetrootforum.jpa.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName name);
}
