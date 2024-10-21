package com.Familyship.checkkuleogi.domains.user.service;

import com.Familyship.checkkuleogi.domains.user.domain.SiteUser;
import com.Familyship.checkkuleogi.domains.user.domain.enums.Role;
import com.Familyship.checkkuleogi.domains.user.domain.repository.UserRepository;
import com.Familyship.checkkuleogi.domains.user.dto.request.CreateUserRequestDTO;
import com.Familyship.checkkuleogi.domains.user.dto.request.LoginUserRequestDTO;
import com.Familyship.checkkuleogi.domains.user.dto.response.CreateUserResponseDTO;
import com.Familyship.checkkuleogi.domains.user.dto.response.LoginUserResponseDTO;
import com.Familyship.checkkuleogi.global.domain.exception.IllegalParameterException;
import com.Familyship.checkkuleogi.global.domain.exception.NotFoundException;
import com.Familyship.checkkuleogi.security.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Transactional
    public CreateUserResponseDTO join(CreateUserRequestDTO createUserRequest){
        SiteUser member = SiteUser.builder()
                .id(createUserRequest.getId())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))  //비밀번호 인코딩
                .role(Role.USER)
                .name(createUserRequest.getName())
                .build();

        try{
            userRepository.save(member);

            return CreateUserResponseDTO.builder()
                    .message("회원가입 성공")
                    .build();
        }catch(Exception e){
            return CreateUserResponseDTO.builder()
                    .message("회원가입 실패")
                    .build();
        }
    }

    @Transactional
    public LoginUserResponseDTO login(LoginUserRequestDTO loginUserRequest){
        SiteUser user = userRepository.findById(loginUserRequest.getId())
                .orElseThrow(() -> new NotFoundException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(loginUserRequest.getPassword(), user.getPassword())) {
            throw new IllegalParameterException("잘못된 비밀번호입니다.");
        }
        // 로그인에 성공하면 email, roles 로 토큰 생성 후 반환
        String token = jwtProvider.createToken(user.getIdx(), user.getAuthorities());

        return LoginUserResponseDTO.builder()
                .token(token)
                .build();
    }

}
