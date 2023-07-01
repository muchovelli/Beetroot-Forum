package com.beetrootforum.beetrootforum.controllers;

import com.beetrootforum.beetrootforum.config.JwtUtils;
import com.beetrootforum.beetrootforum.data.user.FullUserData;
import com.beetrootforum.beetrootforum.enums.RoleName;
import com.beetrootforum.beetrootforum.forms.RegisterForm;
import com.beetrootforum.beetrootforum.jpa.Role;
import com.beetrootforum.beetrootforum.payload.request.LoginRequest;
import com.beetrootforum.beetrootforum.payload.request.SingupRequest;
import com.beetrootforum.beetrootforum.payload.response.JwtResponse;
import com.beetrootforum.beetrootforum.payload.response.MessageResponse;
import com.beetrootforum.beetrootforum.repository.RoleRepository;
import com.beetrootforum.beetrootforum.repository.UserRepository;
import com.beetrootforum.beetrootforum.services.KeyService;
import com.beetrootforum.beetrootforum.services.UserDetailsImpl;
import com.beetrootforum.beetrootforum.services.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    UserDetailsServiceImpl userService;
    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    RoleRepository roleRepository;
    KeyService keyService;
    PasswordEncoder encoder;
    JwtUtils jwtUtils;

    @Autowired
    public AuthController(UserDetailsServiceImpl userService, AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, KeyService keyService, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.keyService = keyService;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPublicKey()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SingupRequest signUpRequest) {
        LOG.debug("Registering user: {} {} {} {}", signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPublicKey(), signUpRequest.getRole());
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Email is already taken!"));
        }

        // Create new user's account
        //TODO change password to public key
        FullUserData user = new FullUserData(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                keyService.convert(signUpRequest.getPublicKey()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByRoleName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        Role adminRole = roleRepository.findByRoleName(RoleName.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                    }
                    case "mod" -> {
                        Role modRole = roleRepository.findByRoleName(RoleName.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                    }
                    default -> {
                        Role userRole = roleRepository.findByRoleName(RoleName.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                }
            });
        }

        user.setRoles(roles);
        userService.save(user.getUsername(), user.getEmail(), user.getPublicKey(), user.getRoles());

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    //TODO change void to ResponseEntity, add sending public key tokens to register page
    @PostMapping("/setPublicKey")
    public void setPublicKey(String publicKey) {

    }

    //TODO decoding email and username from key, add generated encoded random token and captcha
    @PostMapping("/registerUser")
    public void registerUser(@Valid RegisterForm registerForm) {

    }
}
