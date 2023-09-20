package com.example.ali.controller;

import com.example.ali.dto.OrderRequestDto;
import com.example.ali.dto.OrderStatusRequestDto;
import com.example.ali.dto.OrdersResponseDto;
import com.example.ali.security.SellerDetailsImpl;
import com.example.ali.security.UserDetailsImpl;
import com.example.ali.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrdersController {
    private final OrdersService ordersService;

    // 주문 확인 조회
    @GetMapping("/seller/orders")
    public List<OrdersResponseDto> getSellerOrders(@AuthenticationPrincipal SellerDetailsImpl sellerDetails) {
        return ordersService.getSellerOrders(sellerDetails.getSeller());
    }


    // 배송 상태 변경
    @GetMapping("/seller/orders/{ordersId}")
    public ResponseEntity<?> changeDeliveryStatus(@PathVariable Long ordersId, @RequestBody OrderStatusRequestDto orderStatusRequestDto, @AuthenticationPrincipal SellerDetailsImpl sellerDetails) {
        return ordersService.changeDeliveryStatus(ordersId, orderStatusRequestDto, sellerDetails.getSeller());
    }

    // 상품 주문
    @PostMapping("/product/order")
    public ResponseEntity<?> orderProduct(@RequestBody OrderRequestDto orderRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ordersService.orderProduct(orderRequestDto, userDetails.getUser());
    }

    // 주문 조회 (유저 본인의 주문 내역 조회)
    @GetMapping("user/orders")
    public List<OrdersResponseDto> getUserOrders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ordersService.getUserOrders(userDetails.getUser());
    }

}
