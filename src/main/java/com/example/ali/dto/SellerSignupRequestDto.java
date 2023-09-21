package com.example.ali.dto;

import lombok.Getter;

@Getter
public class SellerSignupRequestDto {
    private String username;
    private String password;
    private String storeName;
    private String info;

    public SellerSignupRequestDto(String testSeller, String number, String storeName, String storeInfo) {
        this.username = testSeller;
        this.password = number;
        this.storeName = storeName;
        this.info = storeInfo;
    }
}
