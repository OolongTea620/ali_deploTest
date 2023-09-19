package com.example.ali.service;

import com.example.ali.dto.MessageResponseDto;
import com.example.ali.dto.ProductRequestDto;
import com.example.ali.dto.ProductResponseDto;
import com.example.ali.entity.Product;
import com.example.ali.entity.ProductStock;
import com.example.ali.entity.Seller;
import com.example.ali.repository.ProductRepository;
import com.example.ali.repository.ProductStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductStockRepository productStockRepository;

    @Transactional
    public ResponseEntity<?> createProduct(ProductRequestDto requestDto, Seller seller) {
        Product product = new Product(requestDto, seller);
        Product newProduct = productRepository.save(product);
        ProductStock productStock = new ProductStock(newProduct, requestDto.getStock());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponseDto("상품 등록 성공", new ProductResponseDto(newProduct, productStock.getStock())));
    }

    @Transactional
    public ResponseEntity<?> updateProduct(Long productId, ProductRequestDto requestDto) {
        Product product = findProductById(productId);
        product.update(requestDto);
//        ProductStock productStock = productStockRepository.findProductStockByProduct(product);
        //이거 에바임 양방향 맺어야됨;;;
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new MessageResponseDto("상품 수정 성공", new ProductResponseDto(product)));
    }

    @Transactional
    public ResponseEntity<?> deleteProduct(Long productId) {
        Product product = findProductById(productId);
        productRepository.delete(product);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("상품 삭제 성공", new ProductResponseDto(product)));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getSearchProduct(String keyword) {
        List<Product> productList = new ArrayList<>();

        //keyword 입력이 없을 경우 전체 리스트 조회..... 이렇게 구현해도되는지 모르겠음
        if (keyword == null) {
            productList = productRepository.findAll();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(productList.stream().map(ProductResponseDto::new).toList());
        }

        productList = productRepository.findAllByProductNameLike(keyword);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productList.stream().map(ProductResponseDto::new).toList());
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("product 찾을 수 없음"));
    }



}
