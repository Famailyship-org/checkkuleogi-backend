package com.Familyship.checkkuleogi.domains.recommend.service;

import com.Familyship.checkkuleogi.domains.recommend.dto.RecommendResponseDto;

import java.util.List;

public interface RecommendService {
    List<RecommendResponseDto> getRecommendByChildIdx(Long childIdx);
}
