package com.example.ali.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // test
public class StoreRequestDto {
    private Long sellerId;
    private String storeName;
    private String info;

    public StoreRequestDto(Long sellerId, String storeName, String info) {
        this.sellerId = sellerId;
        this.storeName = storeName;
        this.info = info;
    }

    public StoreRequestDto() {

    }
}
