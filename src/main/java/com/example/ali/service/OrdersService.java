package com.example.ali.service;

import com.example.ali.dto.OrderRequestDto;
import com.example.ali.dto.OrderStatusRequestDto;
import com.example.ali.dto.OrdersResponseDto;
import com.example.ali.entity.Orders;
import com.example.ali.entity.Product;
import com.example.ali.entity.Seller;
import com.example.ali.entity.User;
import com.example.ali.repository.OrdersRepository;
import com.example.ali.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;


    // 상품 주문
    @Transactional
    public ResponseEntity<?> orderProduct(OrderRequestDto orderRequestDto, User user) {
        Product product = findProduct(orderRequestDto.getProductId());
        // 재고 확인
        if(!(product.getProductStock().getStock() < orderRequestDto.getQnt())) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }

        // 재고 변경
        product.getProductStock().changeStock(orderRequestDto.getQnt());

        // 유저 소지금 변경
        user.getUserWallet().changePoint(orderRequestDto.getQnt() * product.getPrice());

        // 주문 생성
        ordersRepository.save(new Orders(orderRequestDto, user, product));
        return ResponseEntity.ok().body("주문이 완료되었습니다.");
    }

    // 유저 주문 조회
    public List<OrdersResponseDto> getUserOrders(User user) {
        return ordersRepository.findAllByUser(user).stream().map(OrdersResponseDto::new).toList();
    }

    // 셀러 주문 조회
    public List<OrdersResponseDto> getSellerOrders(Seller seller) {
        return ordersRepository.findByProductSellerUsername(seller.getUsername()).stream().map(OrdersResponseDto::new).toList();
    }

    // 배송 상태 변경
    public ResponseEntity<?> changeDeliveryStatus(Long ordersId, OrderStatusRequestDto orderStatusRequestDto, Seller seller) {
        Orders orders = findOrders(ordersId);
        // 셀러 확인
        if(!orders.getProduct().getSeller().getUsername().equals(seller.getUsername())) {
            throw new IllegalArgumentException("해당 주문을 변경할 수 없습니다.");
        }
        // 배송 상태 변경
        orders.changeDeliveryStatus(orderStatusRequestDto.getStatus());
        return ResponseEntity.ok().body("배송 상태가 변경되었습니다.");
    }

    // 주문 찾기
    private Orders findOrders(Long id) {
        return ordersRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다.")
        );
    }


    // 상품 찾기
    private Product findProduct(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다.")
        );
    }
}
