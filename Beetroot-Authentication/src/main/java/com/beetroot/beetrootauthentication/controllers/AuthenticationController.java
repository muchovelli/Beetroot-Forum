package com.beetroot.beetrootauthentication.controllers;

import com.beetroot.beetrootauthentication.services.PGPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/auth")
public class AuthenticationController {

    private final PGPService pgpService;

    @Autowired
    public AuthenticationController(PGPService pgpService) {
        this.pgpService = pgpService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate() {
        return null;
    }
}
