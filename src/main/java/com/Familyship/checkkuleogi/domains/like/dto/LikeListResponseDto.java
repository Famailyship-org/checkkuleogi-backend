package com.Familyship.checkkuleogi.domains.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class LikeListResponseDto {
    private List<LikeResponseDto> likes;
}

