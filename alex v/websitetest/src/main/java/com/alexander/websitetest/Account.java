package com.alexander.websitetest;

import org.springframework.data.annotation.Id;

public class Account {
    
    @Id
    public String id;
    public String user;
    public byte[] hash;
    public byte[] salt;
    public String pepper;

    public Account() {}

    public Account(String user, byte[] hash, byte[] salt) {
        this.user = user;
        this.hash = hash;
        this.salt = salt;
    }

    @Override
    public String toString() {
        return String.format(
        "Customer[id=%s, user='%s']",
        id, user);
    }
}