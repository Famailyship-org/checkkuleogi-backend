package com.Familyship.checkkuleogi.domains.child.presentation;

import com.Familyship.checkkuleogi.domains.child.dto.CreateChildRequestDTO;
import com.Familyship.checkkuleogi.domains.child.dto.CreateChildResponseDTO;
import com.Familyship.checkkuleogi.domains.child.service.ChildService;
import com.Familyship.checkkuleogi.global.domain.response.CommonResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.Familyship.checkkuleogi.global.domain.response.CommonResponseEntity.success;

@RestController
@RequestMapping("/api/v1/child")
@RequiredArgsConstructor
public class ChildController {

    private final ChildService childService;

    @PostMapping("/mbti")
    public CommonResponseEntity<CreateChildResponseDTO> createMBTI(@RequestBody CreateChildRequestDTO childCreateRequestDTO) {
        return success(childService.createMBTI(childCreateRequestDTO));
    }
}