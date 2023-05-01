package com.beetrootforum.beetrootforum.services;


import com.beetrootforum.beetrootforum.data.user.FullUserData;
import com.beetrootforum.beetrootforum.jpa.User;
import com.beetrootforum.beetrootforum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(convert(user));
    }

    private FullUserData convert(User user) {
        return new FullUserData(
                user.getUsername(),
                user.getEmail(),
                user.getPublicKey(),
                user.getRoles()
        );
    }
}
