package com.example.ali.dto;

import lombok.Getter;

@Getter
public class SellerSignupRequestDto {
    private String username;
    private String password;
    private String storeName;
    private String info;

    public SellerSignupRequestDto(String testSeller, String password, String storeName, String storeinfo) {
        this.username = testSeller;
        this.password = password;
        this.storeName = storeName;
        this.info = storeinfo;
    }
}
