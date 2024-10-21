package com.Familyship.checkkuleogi.domains.user.dto.response;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class LoginUserResponseDTO {
    private String token;
}
