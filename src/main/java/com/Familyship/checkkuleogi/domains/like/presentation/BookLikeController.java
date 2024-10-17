package com.Familyship.checkkuleogi.domains.like.presentation;

import com.Familyship.checkkuleogi.domains.like.dto.LikeDto;
import com.Familyship.checkkuleogi.domains.like.dto.LikeListResponseDto;
import com.Familyship.checkkuleogi.domains.like.dto.LikeResponseDto;

import com.Familyship.checkkuleogi.domains.like.service.BookLikeService;
import com.Familyship.checkkuleogi.global.domain.response.CommonResponseEntity;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/like")
@RequiredArgsConstructor
public class BookLikeController {

    private final BookLikeService bookLikeService;

    @PostMapping("")
    public CommonResponseEntity<LikeResponseDto> createBookLike(@RequestBody LikeDto dto) {
        return CommonResponseEntity.success(bookLikeService.createLike(dto));
    }

    @GetMapping("/{child_idx}")
    public CommonResponseEntity<LikeListResponseDto> getLikeDislike(@PathVariable("child_idx") Long childIdx) {
        return CommonResponseEntity.success(bookLikeService.getLikeDislike(childIdx));
    }

    @GetMapping("/likes/{child_idx}")
    public CommonResponseEntity<LikeListResponseDto> getLikes(@PathVariable("child_idx") Long childIdx) {
        return CommonResponseEntity.success(bookLikeService.getLikes(childIdx));
    }

    @GetMapping("/dislikes/{child_idx}")
    public CommonResponseEntity<LikeListResponseDto> getDislikes(@PathVariable("child_idx") Long childIdx) {
        return CommonResponseEntity.success(bookLikeService.getDislikes(childIdx));
    }

    @PatchMapping("")
    public CommonResponseEntity<String> changeLikeDislike(@RequestBody LikeDto dto) {
        bookLikeService.updateLike(dto);
        return CommonResponseEntity.success("좋아요 싫어요 변경 완료");
    }

    @DeleteMapping("")
    public CommonResponseEntity<String> deleteLikeDisLike(@RequestBody LikeDto dto) {
        bookLikeService.deleteLike(dto);
        return CommonResponseEntity.success("좋아요 싫어요 삭제 완료");
    }
}
