package com.example.ali.controller;

import com.example.ali.dto.ProductRequestDto;
import com.example.ali.security.SellerDetailsImpl;
import com.example.ali.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    //seller 상품 등록
    @PostMapping("/seller/product")
    public ResponseEntity<?> createProduct(
            @AuthenticationPrincipal SellerDetailsImpl sellerDetails,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart(value = "requestDto", required = false) ProductRequestDto requestDto) throws IOException {
        return productService.createProduct(requestDto, sellerDetails.getSeller(), image);
    }

    //seller 상품 수정
    @PutMapping("/seller/product/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId,
                                           @RequestBody ProductRequestDto requestDto) {
        return productService.updateProduct(productId, requestDto);
    }

    //seller 상품 삭제
    @DeleteMapping("/seller/product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        return productService.deleteProduct(productId);
    }

    //전체, 검색 상품 조회
    @GetMapping("/products")
    public ResponseEntity<?> getSearchProduct(@RequestParam String keyword) {
        return productService.getSearchProduct(keyword);
    }
}
