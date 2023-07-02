package com.beetroot.beetrootauthentication.models;

public class PGPKey {

    private String publicKey;

    public PGPKey() {
    }

    public PGPKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPublicKey() {
        return publicKey;
    }
}
