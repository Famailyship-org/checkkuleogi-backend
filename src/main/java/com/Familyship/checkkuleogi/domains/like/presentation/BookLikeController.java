package com.Familyship.checkkuleogi.domains.like.presentation;

import com.Familyship.checkkuleogi.domains.like.domain.BookLike;
import com.Familyship.checkkuleogi.domains.like.dto.LikeDto;
import com.Familyship.checkkuleogi.domains.like.service.BookLikeServcie;
import com.Familyship.checkkuleogi.global.domain.response.CommonResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookLikeController {
    private final BookLikeServcie bookLikeService;

    @PostMapping("/like")
    public CommonResponseEntity<LikeDto> createBookLike(@RequestParam Long childIdx,
                                                         @RequestParam Long bookIdx,
                                                         @RequestParam boolean isLike) {
        BookLike bookLike = bookLikeService.createLike(childIdx, bookIdx, isLike);

        LikeDto res = new LikeDto(
                bookLike.getChild().getIdx(),
                bookLike.getBook().getIdx(),
                bookLike.isLike()
        );
        return CommonResponseEntity.success(res);
    }
}
