package com.example.ali.security;

import com.example.ali.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JWT 검증 및 인가")
//authfilter,loggingfilter 대신 편리하게 사용
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomLoginService customLoginService;
    public JwtAuthorizationFilter(JwtUtil jwtUtil, CustomLoginService customLoginServic) {
        this.jwtUtil = jwtUtil;
        this.customLoginService = customLoginServic;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtUtil.getHeaderToken(req, "Access");
        String refreshToken = jwtUtil.getHeaderToken(req, "Refresh");
        String userType = jwtUtil.getUserTypeFromToken(accessToken); // 사용자 유형 정보 추출

        if (accessToken != null) {
            // 어세스 토큰값이 유효하다면 setAuthentication를 통해
            // security context에 인증 정보저장
            if (jwtUtil.validateToken(accessToken)) {
                setAuthentication(jwtUtil.getUserNameFromToken(accessToken), userType);
            }
            // 어세스 토큰이 만료된 상황 && 리프레시 토큰 또한 존재하는 상황
            else if (refreshToken != null) {
                // 리프레시 토큰 검증 && 리프레시 토큰 DB에서  토큰 존재유무 확인
                boolean isRefreshToken = jwtUtil.refreshTokenValidation(refreshToken);
                // 리프레시 토큰이 유효하고 리프레시 토큰이 DB와 비교했을때 똑같다면
                if (isRefreshToken) {
                    // 리프레시 토큰으로 아이디 정보 가져오기
                    String username = jwtUtil.getUserNameFromToken(refreshToken);
                    userType = jwtUtil.getUserTypeFromToken(refreshToken); // 리프레시 토큰에서 사용자 유형 정보 추출
                    // 새로운 어세스 토큰 발급
                    String newAccessToken = jwtUtil.createToken(username, userType, "Access");
                    // 헤더에 어세스 토큰 추가
                    jwtUtil.setHeaderAccessToken(res, newAccessToken);
                    // Security context에 인증 정보 넣기
                    setAuthentication(jwtUtil.getUserNameFromToken(newAccessToken), userType);
                }
                // 리프레시 토큰이 만료 || 리프레시 토큰이 DB와 비교했을때 똑같지 않다면
                else {
                    throw new NullPointerException("Refresh Token 만료");
                }
            }
        }

        filterChain.doFilter(req, res);
    }

    // 인증 처리
    public void setAuthentication(String username, String userType) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username, userType);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username, String userType) {
        UserDetails userDetails = customLoginService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}