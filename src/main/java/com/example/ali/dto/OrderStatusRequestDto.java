package com.example.ali.dto;

import com.example.ali.entity.OrderStatus;
import lombok.Getter;

@Getter
public class OrderStatusRequestDto {
    private OrderStatus status;
}
