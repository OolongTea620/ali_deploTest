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
}

