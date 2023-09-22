package com.example.ali.dto;


import lombok.Getter;

@Getter
public class UserSignupRequestDto {
    private String username;
    private String password;
    private String email;

    public UserSignupRequestDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
