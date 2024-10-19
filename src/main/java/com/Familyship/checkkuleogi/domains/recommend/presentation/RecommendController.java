package com.Familyship.checkkuleogi.domains.recommend.presentation;

import com.Familyship.checkkuleogi.domains.recommend.dto.RecommendResponseDto;
import com.Familyship.checkkuleogi.domains.recommend.service.RecommendService;
import com.Familyship.checkkuleogi.global.domain.response.CommonResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recommend")
@RequiredArgsConstructor
public class RecommendController {
    private final RecommendService recommendService;

    @GetMapping("/{child_idx}")
    public CommonResponseEntity<List<RecommendResponseDto>> getRecommedBooks(@PathVariable("child_idx") Long childIdx){
        return CommonResponseEntity.success(recommendService.getRecommendByChildIdx(childIdx));
    }
}
