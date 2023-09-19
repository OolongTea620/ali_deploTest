package com.example.ali.security;

import com.example.ali.dto.LoginRequestDto;
import com.example.ali.entity.RefreshToken;
import com.example.ali.jwt.JwtUtil;
import com.example.ali.repository.RefreshTokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sparta.post.dto.TokenDto;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성")
//authfilter,loggingfilter 대신 편리하게 사용
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private static final ObjectMapper mapper = new ObjectMapper();

    private SellerDetailsServiceImpl sellerDetailsServiceImpl;
    private UserDetailsServiceImpl userDetailsServiceImpl;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, RefreshTokenRepository refreshTokenRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            // 요청 본문이 비어 있는지 확인
            if (request.getContentLength() == 0) {
                throw new RuntimeException("요청 본문이 비어 있습니다.");
            }
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            String userType = requestDto.getUserType();

            // userType 검증
            if ("SELLER".equals(userType)) {
                UserDetails sellerDetails = sellerDetailsServiceImpl.loadUserByUsername(requestDto.getUsername());
                return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                        sellerDetails.getUsername(),
                        sellerDetails.getPassword(),
                        sellerDetails.getAuthorities()
                    )
                );
            } else{
                UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(requestDto.getUsername());
                return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(),
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                    )
                );
            }
        } catch (IOException e) {
            log.error("예외 발생: ", e);
            throw new RuntimeException("요청 처리 중 오류가 발생했습니다.");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();

        // 아이디 정보로 token 생성
        TokenDto tokenDto = jwtUtil.createAllToken(username);

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUsername(username);

        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        } else {
            RefreshToken newRefreshToken = new RefreshToken(tokenDto.getRefreshToken(),username);
            refreshTokenRepository.save(new RefreshToken(tokenDto.getRefreshToken(),username));
        }
        setHeader(response, tokenDto);

//        String token = jwtUtil.createToken(username, role);
//        jwtUtil.addJwtToCookie(token, response);
    }
    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");
        response.setStatus(401);
        // 추가
        throw new UsernameNotFoundException("회원을 찾을 수 없습니다.");
    }
}