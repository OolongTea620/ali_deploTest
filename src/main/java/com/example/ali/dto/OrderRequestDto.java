package com.example.ali.dto;

import lombok.Getter;

@Getter
public class OrderRequestDto {
    private Long productId;
    private Long qnt;

    public OrderRequestDto(Long productId, Long qnt) {
        this.productId = productId;
        this.qnt = qnt;
    }
}
