package com.example.ali.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // test
public class UserSignupRequestDto {
    private String username;
    private String password;
    private String email;

    public UserSignupRequestDto(String test, String number, String mail) {
        this.username = test;
        this.password = number;
        this.email = mail;
    }
}
