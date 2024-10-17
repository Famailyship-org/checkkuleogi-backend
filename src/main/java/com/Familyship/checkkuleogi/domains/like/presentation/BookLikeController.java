package com.Familyship.checkkuleogi.domains.like.presentation;

import com.Familyship.checkkuleogi.domains.like.domain.BookLike;
import com.Familyship.checkkuleogi.domains.like.dto.LikeDto;
import com.Familyship.checkkuleogi.domains.like.service.BookLikeService;
import com.Familyship.checkkuleogi.global.domain.response.CommonResponseEntity;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/likedislike")
@RequiredArgsConstructor
public class BookLikeController {

    private final BookLikeService bookLikeService;

    @PostMapping("")
    public CommonResponseEntity<LikeDto> createBookLike(@RequestParam Long childIdx,
                                                         @RequestParam Long bookIdx,
                                                         @RequestParam boolean isLike) {
        BookLike bookLike = bookLikeService.createLike(childIdx, bookIdx, isLike);

        LikeDto res = new LikeDto(
                bookLike.getChild().getIdx(),
                bookLike.getBook().getIdx(),
                bookLike.isLikedislike()
        );
        return CommonResponseEntity.success(res);
    }

    @GetMapping("/{child_idx}")
    public CommonResponseEntity<List<Map<String, Object>>> getLikeDislike(@PathVariable("child_idx") Long childIdx) {
        List<BookLike> likedislike = bookLikeService.getLikeDislike(childIdx);

        if (likedislike.isEmpty()) {
            return CommonResponseEntity.error(null, HttpStatus.NOT_FOUND, "좋아요/싫어요 기록이 없습니다.");
        }

        List<Map<String, Object>> result = likedislike.stream()
                .map(ld -> {
                    Map<String, Object> res = new HashMap<>();
                    res.put("book", Map.of(
                            "book_idx", ld.getBook().getIdx(),
                            "title", ld.getBook().getTitle()
                    ));
                    res.put("like", ld.isLikedislike());
                    return res;
                })
                .collect(Collectors.toList());
        return CommonResponseEntity.success(result);
    }

    @GetMapping("/only/{child_idx}")
    public CommonResponseEntity<List<Map<String, Object>>> getLikes(
            @PathVariable("child_idx") Long childIdx,
            @RequestParam boolean isLike) {
        List<BookLike> likes = bookLikeService.getLikesDislikes(childIdx, isLike);

        if (likes.isEmpty()) {
            if(isLike) {
                return CommonResponseEntity.error(null, HttpStatus.NOT_FOUND, "좋아요 기록이 없습니다.");
            }else{
                return CommonResponseEntity.error(null, HttpStatus.NOT_FOUND, "싫어요 기록이 없습니다.");
            }

        }
        List<Map<String, Object>> result = likes.stream()
                .map(like -> {
                    Map<String, Object> res = new HashMap<>();
                    res.put("book", Map.of(
                            "book_idx", like.getBook().getIdx(),
                            "title", like.getBook().getTitle()
                    ));
                    return res;
                })
                .collect(Collectors.toList());
        return CommonResponseEntity.success(result);
    }

    @PutMapping("/change")
    public CommonResponseEntity<String> changeLikeDislike(@RequestParam Long childIdx, @RequestParam Long bookIdx) {
        bookLikeService.updateLike(childIdx, bookIdx);
        return CommonResponseEntity.success("좋아요 싫어요 변경 완료");
    }

    @DeleteMapping("/delete")
    public CommonResponseEntity<String> deleteLike(@RequestParam Long childIdx, @RequestParam Long bookIdx) {
        bookLikeService.deleteLike(childIdx, bookIdx);
        return CommonResponseEntity.success("좋아요 싫어요 삭제 완료"); // 삭제가 성공했음을 나타냄
    }
}
