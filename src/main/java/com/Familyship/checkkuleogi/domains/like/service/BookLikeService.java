package com.Familyship.checkkuleogi.domains.like.service;


import com.Familyship.checkkuleogi.domains.like.dto.LikeDto;
import com.Familyship.checkkuleogi.domains.like.dto.LikeListResponseDto;
import com.Familyship.checkkuleogi.domains.like.dto.LikeResponseDto;

public interface BookLikeService {

    LikeResponseDto createLike(LikeDto dto);

    LikeListResponseDto getLikeDislike(Long childIdx);

    LikeListResponseDto getLikes(Long childIdx);

    LikeListResponseDto getDislikes(Long childIdx);

    void updateLike(LikeDto dto);

    void deleteLike(LikeDto dto);

}
