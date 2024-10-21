package com.Familyship.checkkuleogi.domains.user.dto.request;

import lombok.Getter;

@Getter
public class CreateUserRequestDTO {
    private String id;
    private String password;
    private String name;
}
