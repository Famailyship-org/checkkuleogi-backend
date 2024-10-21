package com.Familyship.checkkuleogi.domains.test.service;

import com.Familyship.checkkuleogi.domains.test.dto.response.Test1ResponseDto;
import com.Familyship.checkkuleogi.security.jwt.JwtProvider;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    private final JwtProvider jwtProvider;

    public Test1ResponseDto checkTokenTest(HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        Long id = Long.valueOf(jwtProvider.getUserIdFromToken(token));
        Authentication role = jwtProvider.getAuthentication(token);
        return Test1ResponseDto.builder()
                .id(id)
                .role(role)
                .build();
    }
}
