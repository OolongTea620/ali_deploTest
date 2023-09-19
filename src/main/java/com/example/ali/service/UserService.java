package com.example.ali.service;

import com.example.ali.dto.MessageResponseDto;
import com.example.ali.dto.UserSignupRequestDto;
import com.example.ali.entity.User;
import com.example.ali.repository.UserRepository;
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
    private final PasswordEncoder passwordEncoder;


    public ResponseEntity<?> signup(UserSignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // username 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if(checkUsername.isPresent()){
            throw new IllegalArgumentException("중복된 사용자");
        }

        // 사용자 등록
        User user = new User(requestDto, password);
        userRepository.save(user);

        return new ResponseEntity<>(new MessageResponseDto("회원가입 성공"), null, HttpStatus.OK);
    }


}
