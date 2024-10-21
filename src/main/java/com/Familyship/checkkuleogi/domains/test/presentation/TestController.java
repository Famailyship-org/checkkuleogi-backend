package com.Familyship.checkkuleogi.domains.test.presentation;

import com.Familyship.checkkuleogi.domains.test.dto.request.Test1RequestDTO;
import com.Familyship.checkkuleogi.domains.test.dto.response.Test1ResponseDto;
import com.Familyship.checkkuleogi.domains.test.service.TestService;
import com.Familyship.checkkuleogi.domains.user.dto.request.CreateUserRequestDTO;
import com.Familyship.checkkuleogi.global.domain.response.CommonResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.Familyship.checkkuleogi.global.domain.response.CommonResponseEntity.success;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    @GetMapping("/test1")
    public CommonResponseEntity<Test1ResponseDto> getTest1(@RequestBody Test1RequestDTO requestDTO) {
        return success(testService.checkTokenTest(requestDTO));
    }
}
