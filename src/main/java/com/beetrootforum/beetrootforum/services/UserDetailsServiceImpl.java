package com.beetrootforum.beetrootforum.services;


import com.beetrootforum.beetrootforum.data.KeyData;
import com.beetrootforum.beetrootforum.data.user.FullUserData;
import com.beetrootforum.beetrootforum.jpa.Key;
import com.beetrootforum.beetrootforum.jpa.Role;
import com.beetrootforum.beetrootforum.jpa.User;
import com.beetrootforum.beetrootforum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final KeyService keyService;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, KeyService keyService) {
        this.userRepository = userRepository;
        this.keyService = keyService;
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
                keyService.convert(user.getPublicKey()),
                user.getRoles()
        );
    }

    @Transactional
    public void save(String username, String email, KeyData publicKey, Set<Role> roles) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPublicKey(new Key(publicKey.getPublicKey(), publicKey.getUser()));
        user.setRoles(roles);
        userRepository.save(user);
    }
}
