package com.Familyship.checkkuleogi.domains.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeDto {
    private Long childIdx;
    private Long bookIdx;
    private boolean likedislike;
}


