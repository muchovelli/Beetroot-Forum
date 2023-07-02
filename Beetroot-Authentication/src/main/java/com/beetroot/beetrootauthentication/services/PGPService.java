package com.beetroot.beetrootauthentication.services;

import org.springframework.stereotype.Service;

@Service
public class PGPService {

    public String encrypt(String message, String publicKey) {
        return "Encrypted message";
    }

    public String decrypt(String message, String privateKey) {
        return "Decrypted message";
    }
}
