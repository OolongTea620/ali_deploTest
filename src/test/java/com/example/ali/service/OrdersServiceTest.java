package com.example.ali.service;

import com.example.ali.dto.OrderRequestDto;
import com.example.ali.dto.OrdersResponseDto;
import com.example.ali.entity.*;
import com.example.ali.repository.OrdersRepository;
import com.example.ali.repository.ProductRepository;
import com.example.ali.repository.SellerRepository;
import com.example.ali.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// Mock 사용을 위해 설정
class OrdersServiceTest {

    @InjectMocks
    private OrdersService ordersService;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;




    @Test
    @DisplayName("상품 주문 - 성공")
    void test() {

    }

}