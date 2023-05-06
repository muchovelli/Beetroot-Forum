package com.beetrootforum.beetrootforum.data;

import com.beetrootforum.beetrootforum.jpa.User;

public class KeyData {

    private String publicKey;
    private User user;

    public KeyData(String pemKey, User user) {
        this.publicKey = pemKey;
        this.user = user;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
