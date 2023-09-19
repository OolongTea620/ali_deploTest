package com.example.ali.service;

import com.example.ali.dto.MessageResponseDto;
import com.example.ali.dto.SellerSignupRequestDto;
import com.example.ali.entity.Seller;
import com.example.ali.repository.SellerRepository;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;


    public ResponseEntity<?> signup(SellerSignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String storeName = requestDto.getStoreName();
        String info = requestDto.getInfo();


        // username 중복 확인
        Optional<Seller> checkUsername = sellerRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("ID가 중복입니다.");
        }

        // storeName 중복 확인
        Optional<Seller> checkStoreName = sellerRepository.findByStoreName(storeName);
        if (checkStoreName.isPresent()) {
            throw new IllegalArgumentException("셀러명이 중복입니다.");
        }

        // 셀러 등록
        Seller seller = new Seller(requestDto, password);
        sellerRepository.save(seller);

        return new ResponseEntity<>(new MessageResponseDto("회원가입 성공"), null, HttpStatus.OK);

    }

}
