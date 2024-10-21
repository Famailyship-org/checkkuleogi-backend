package com.Familyship.checkkuleogi.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;

import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    String secretKey;

    private final UserDetailsService userDetailsService;

    private Key key; // Key 객체로 secretKey 저장

    @PostConstruct
    protected void init() {
        // Base64 인코딩 후 Key 객체로 변환
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey)); // Key 객체 생성
    }

    // JWT 토큰 생성
    public String createToken(Long userPk, Collection<? extends GrantedAuthority> authorities) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userPk)); // JWT payload 에 저장되는 정보

        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)  // GrantedAuthority에서 권한 정보 추출
                .collect(Collectors.toList());

        claims.put("roles", roles); // key-value 쌍으로 역할 저장
        Date now = new Date();

        long tokenValidTime = 1000L * 60 * 60;

        return Jwts.builder()
                .setClaims(claims) // 사용자 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // 토큰 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256)  // Key 객체와 함께 서명
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        // 토큰에서 사용자 정보(PK)를 추출
        String userPk = getUserIdFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userPk);

        // JWT에서 roles 정보로부터 권한 설정
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        // 사용자 정보로 Authentication 객체를 생성하고 반환
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    public String getUserIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 이후의 토큰 부분만 반환
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            return false;
        }
    }

}
