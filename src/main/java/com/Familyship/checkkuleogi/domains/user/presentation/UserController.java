package com.Familyship.checkkuleogi.domains.user.presentation;

import com.Familyship.checkkuleogi.global.domain.exception.NotFoundException;
import com.Familyship.checkkuleogi.global.domain.response.CommonResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.Familyship.checkkuleogi.global.domain.response.CommonResponseEntity.success;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/test")
    public CommonResponseEntity<Object> test(){
        Object a = "int";
        if(a != null){
            throw new NotFoundException("에러를 작성해봐요");
        }
        return success(a);
    }
}