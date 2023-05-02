package com.beetrootforum.beetrootforum.services;

import com.beetrootforum.beetrootforum.enums.RoleName;
import com.beetrootforum.beetrootforum.exceptions.ResourceNotFoundException;
import com.beetrootforum.beetrootforum.forms.RegisterForm;
import com.beetrootforum.beetrootforum.jpa.Role;
import com.beetrootforum.beetrootforum.jpa.User;
import com.beetrootforum.beetrootforum.repository.RoleRepository;
import com.beetrootforum.beetrootforum.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;


@Service
public class AuthService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public User register(RegisterForm registerForm) throws ResourceNotFoundException {
        if (userRepository.findByEmail(registerForm.getEmail()).isPresent()) {
            LOG.error("Email exists: {}", registerForm.getEmail());
        }
        if (userRepository.findByUsername(registerForm.getUsername()) != null) {
            LOG.error("Username exists: {}", registerForm.getUsername());
        }

        User user = new User();
        user.setEmail(registerForm.getEmail());
        user.setPublicKey(registerForm.getPublicKey());
        user.setUsername(registerForm.getUsername());
        user.setPublicKey(registerForm.getPublicKey());
        Role role = roleRepository.findByRoleName(RoleName.ROLE_USER).orElseThrow(() -> new ResourceNotFoundException("Role"));
        user.setRoles(Collections.singleton(role));
        return user;
    }
}
