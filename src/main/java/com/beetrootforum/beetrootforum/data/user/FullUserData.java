package com.beetrootforum.beetrootforum.data.user;

import com.beetrootforum.beetrootforum.jpa.Role;

import java.util.Set;

public class FullUserData extends UserData {
    private Set<Role> roles;

    public FullUserData() {
    }

    public FullUserData(String username, String email, String publicKey) {
        super(username, email, publicKey);
    }

    public FullUserData(String username, String email, String publicKey, Set<Role> roles) {
        super(username, email, publicKey);
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
