package com.example.ali.service;

import com.example.ali.dto.MessageResponseDto;
import com.example.ali.dto.UserSignupRequestDto;
import com.example.ali.entity.Seller;
import com.example.ali.entity.SellerWallet;
import com.example.ali.entity.User;
import com.example.ali.entity.UserWallet;
import com.example.ali.repository.SellerRepository;
import com.example.ali.repository.UserRepository;
import com.example.ali.repository.UserWalletRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserWalletRepository userWalletRepository;
    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;


    public ResponseEntity<?> signup(UserSignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());


        // user username 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        Optional<Seller> checkSellername = sellerRepository.findByUsername(username);
        if(checkUsername.isPresent() || checkSellername.isPresent() ){
            throw new IllegalArgumentException("중복된 사용자");
        }

        // 사용자 지갑 등록
        UserWallet userWallet = new UserWallet();
        userWalletRepository.save(userWallet);

        // 사용자 등록
        User user = new User(requestDto, password, userWallet);
        userRepository.save(user);

        // 사용자 지갑 등록
        userWalletRepository.save(new UserWallet());

        return new ResponseEntity<>(new MessageResponseDto("회원가입 성공"), null, HttpStatus.OK);
    }


}
