package com.projectx.foundit.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Token {
    private int userId;
    private String token;
    private Date timestamp;

}
