package com.Familyship.checkkuleogi.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Authorization 헤더에서 JWT 토큰을 추출
        String token = jwtProvider.resolveToken(request);

        // 토큰이 존재하고 유효한지 확인
        if (token != null && jwtProvider.validateToken(token)) {
            // 토큰이 유효하면 해당 사용자에 대한 인증 정보를 생성
            Authentication auth = jwtProvider.getAuthentication(token);
            System.out.println(auth);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // 나머지 필터 체인 처리
        filterChain.doFilter(request, response);
    }
}
