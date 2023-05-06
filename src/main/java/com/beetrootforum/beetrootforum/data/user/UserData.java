package com.beetrootforum.beetrootforum.data.user;

import com.beetrootforum.beetrootforum.data.KeyData;
import com.beetrootforum.beetrootforum.jpa.Role;

import java.io.Serializable;
import java.util.Set;

public class UserData implements Serializable {

    private Long id;
    private String username;
    private String email;
    private KeyData publicKey;
    private Set<Role> roles;

    public UserData() {
    }

    public UserData(String username, String email, KeyData publicKey) {
        this.username = username;
        this.email = email;
        this.publicKey = publicKey;
    }

    public UserData(Long id, String username, String email, KeyData publicKey) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.publicKey = publicKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public KeyData getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(KeyData publicKey) {
        this.publicKey = publicKey;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
