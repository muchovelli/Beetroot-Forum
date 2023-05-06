package com.beetrootforum.beetrootforum.forms;

import com.beetrootforum.beetrootforum.data.KeyData;
import com.beetrootforum.beetrootforum.jpa.Role;

import java.util.Set;

public class RegisterForm {

    private String username;
    private String password;
    private String email;
    private KeyData publicKey;
    private Set<Role> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
