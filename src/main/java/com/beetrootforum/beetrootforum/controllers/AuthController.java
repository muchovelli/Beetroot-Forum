package com.beetrootforum.beetrootforum.controllers;

import com.beetrootforum.beetrootforum.exceptions.ResourceNotFoundException;
import com.beetrootforum.beetrootforum.forms.RegisterForm;
import com.beetrootforum.beetrootforum.jpa.User;
import com.beetrootforum.beetrootforum.model.ApiResponse;
import com.beetrootforum.beetrootforum.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Controller
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterForm registerForm, BindingResult bindingResult) throws ResourceNotFoundException {
        User user = authService.register(registerForm);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{username}").buildAndExpand(user.getUsername()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "Registration success."));
    }
}
