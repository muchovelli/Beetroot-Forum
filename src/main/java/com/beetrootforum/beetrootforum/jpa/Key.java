package com.beetrootforum.beetrootforum.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Key {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "pem_key", nullable = false)
    public String pemKey;

    @Column(name = "user_id", nullable = false)
    @OneToOne
    public User user;

    public Key() {

    }

    public Key(String pemKey, User user) {
        this.pemKey = pemKey;
        this.user = user;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPemKey() {
        return pemKey;
    }

    public void setPemKey(String pemKey) {
        this.pemKey = pemKey;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
