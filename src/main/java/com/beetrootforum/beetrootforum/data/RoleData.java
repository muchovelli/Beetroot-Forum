package com.beetrootforum.beetrootforum.data;

import com.beetrootforum.beetrootforum.enums.RoleName;

public class RoleData {
    private Long id;
    private RoleName roleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
