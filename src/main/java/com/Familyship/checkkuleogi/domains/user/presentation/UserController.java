package com.Familyship.checkkuleogi.domains.user.presentation;

import com.Familyship.checkkuleogi.domains.user.dto.request.CreateUserRequestDTO;
import com.Familyship.checkkuleogi.domains.user.dto.request.LoginUserRequestDTO;
import com.Familyship.checkkuleogi.domains.user.dto.response.CreateUserResponseDTO;
import com.Familyship.checkkuleogi.domains.user.dto.response.LoginUserResponseDTO;
import com.Familyship.checkkuleogi.domains.user.service.UserService;

import com.Familyship.checkkuleogi.global.domain.response.CommonResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.Familyship.checkkuleogi.global.domain.response.CommonResponseEntity.success;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public CommonResponseEntity<CreateUserResponseDTO> join(@RequestBody CreateUserRequestDTO user) {
        return success(userService.join(user));
    }

    @PostMapping("/login")
    public CommonResponseEntity<LoginUserResponseDTO> login(@RequestBody LoginUserRequestDTO user) {
        return success(userService.login(user));
    }

}