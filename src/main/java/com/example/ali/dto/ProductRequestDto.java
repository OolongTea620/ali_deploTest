package com.example.ali.dto;

import com.example.ali.entity.ProductStatus;
import lombok.Getter;

@Getter
public class ProductRequestDto {
    private String productName;
    private Long price;
    private Long stock;
    private String info;
    private ProductStatus productStatus;

    //테스트코드 전용
    public ProductRequestDto(String productName, Long price, Long stock, String info) {
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.info = info;
    }
}

