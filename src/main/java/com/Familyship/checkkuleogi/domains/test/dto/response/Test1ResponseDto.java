package com.Familyship.checkkuleogi.domains.test.dto.response;

import com.Familyship.checkkuleogi.domains.user.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.Authentication;

@Getter
@Builder
public class Test1ResponseDto {
    private Long id;
    private Authentication role;
}
