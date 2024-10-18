package com.Familyship.checkkuleogi.domains.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeResponseDto {
    private Long childIdx;
    private Long bookIdx;
    private Boolean likedislike;
}
