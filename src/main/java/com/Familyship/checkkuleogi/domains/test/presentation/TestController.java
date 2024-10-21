package com.Familyship.checkkuleogi.domains.test.presentation;

import com.Familyship.checkkuleogi.domains.test.dto.response.Test1ResponseDto;
import com.Familyship.checkkuleogi.domains.test.service.TestService;
import com.Familyship.checkkuleogi.global.domain.response.CommonResponseEntity;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.Familyship.checkkuleogi.global.domain.response.CommonResponseEntity.success;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    @GetMapping("/test1")
    public CommonResponseEntity<Test1ResponseDto> getTest1(HttpServletRequest request) {
        return success(testService.checkTokenTest(request));
    }
}
