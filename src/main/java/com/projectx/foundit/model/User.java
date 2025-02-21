package com.projectx.foundit.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private String id;
    private String username;
    private String email;


    public User(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
