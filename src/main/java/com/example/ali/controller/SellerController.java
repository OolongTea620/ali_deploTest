package com.example.ali.controller;

import com.example.ali.dto.SellerSignupRequestDto;
import com.example.ali.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth/seller")
public class SellerController {

    private final SellerService sellerService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SellerSignupRequestDto requestDto){
        return sellerService.signup(requestDto);
    }
}
