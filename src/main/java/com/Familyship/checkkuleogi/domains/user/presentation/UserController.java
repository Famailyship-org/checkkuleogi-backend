package com.Familyship.checkkuleogi.domains.user.presentation;

import com.Familyship.checkkuleogi.domains.user.dto.request.CreateUserRequest;
import com.Familyship.checkkuleogi.domains.user.dto.request.LoginUserRequest;
import com.Familyship.checkkuleogi.domains.user.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Long join(@RequestBody CreateUserRequest user) {
        return userService.join(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginUserRequest user) {
        return userService.login(user);
    }

}