package com.Familyship.checkkuleogi.domains.like.service;


import com.Familyship.checkkuleogi.domains.book.domain.Book;
import com.Familyship.checkkuleogi.domains.book.domain.repository.BookRepository;
import com.Familyship.checkkuleogi.domains.child.domain.Child;
import com.Familyship.checkkuleogi.domains.child.domain.repository.ChildRepository;
import com.Familyship.checkkuleogi.domains.like.domain.repository.BookLikeRepository;
import com.Familyship.checkkuleogi.domains.like.domain.BookLike;
import com.Familyship.checkkuleogi.domains.like.exception.DuplicateLikeException;
import com.Familyship.checkkuleogi.global.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookLikeService {
    private final BookLikeRepository bookLikeRepository;
    private final ChildRepository childRepository;
    private final BookRepository bookRepository;

    public BookLikeService(BookLikeRepository bookLikeRepository, ChildRepository childRepository, BookRepository bookRepository) {
        this.bookLikeRepository = bookLikeRepository;
        this.childRepository = childRepository;
        this.bookRepository = bookRepository;
    }

    public BookLike createLike(Long childIdx, Long bookIdx, boolean isLike) {
        //중복 검증
        if(bookLikeRepository.existsByChildIdxAndBookIdx(childIdx, bookIdx)) {
            throw new DuplicateLikeException("이미 좋아요/싫어요를 눌렀습니다.");
        }
        //존재하는 Child인가?
        Child child = childRepository.findById(childIdx)
                .orElseThrow(() -> new NotFoundException("어린이를 찾을 수 없습니다."));
        //존재하는 Book인가?
        Book book = bookRepository.findById(bookIdx)
                .orElseThrow(() -> new NotFoundException("책을 찾을 수 없습니다."));

        BookLike bookLike = BookLike.builder()
                .child(child)
                .book(book)
                .likedislike(isLike)
                .build();

        return bookLikeRepository.save(bookLike);
    }

    //전체 목록 조회
    public List<BookLike> getLikeDislike(Long childIdx){
        Child child = childRepository.findById(childIdx)
                .orElseThrow(() -> new NotFoundException("어린이를 찾을 수 없습니다."));
        return bookLikeRepository.findByChild(child);
    }

    //좋아요만 & 싫어요만 조회
    public List<BookLike> getLikesDislikes(Long childIdx, boolean isLike) {
        Child child = childRepository.findById(childIdx)
                .orElseThrow(() -> new NotFoundException("어린이를 찾을 수 없습니다."));
        return bookLikeRepository.findByChildAndLikedislike(child, isLike);
    }

    //좋아요 -> 싫어요 or 싫어요 -> 좋아요
    public void updateLike(Long childIdx, Long bookIdx) {
        childRepository.findById(childIdx)
                .orElseThrow(() -> new NotFoundException("어린이를 찾을 수 없습니다."));

        bookRepository.findById(bookIdx)
                .orElseThrow(() -> new NotFoundException("책을 찾을 수 없습니다."));

        BookLike existbookLike = bookLikeRepository.findByChildIdxAndBookIdx(childIdx, bookIdx)
                .orElseThrow(() -> new NotFoundException("좋아요 기록을 찾을 수 없습니다."));

        // 좋아요/싫어요 상태 변경
        boolean newLikeDislike = !existbookLike.isLikedislike();

        existbookLike.setLikedislike(newLikeDislike);

        bookLikeRepository.save(existbookLike);
    }

    //삭제
    public void deleteLike(Long childIdx, Long bookIdx) {
        childRepository.findById(childIdx)
                .orElseThrow(() -> new NotFoundException("어린이를 찾을 수 없습니다."));

        bookRepository.findById(bookIdx)
                .orElseThrow(() -> new NotFoundException("책을 찾을 수 없습니다."));

        BookLike bookLike = bookLikeRepository.findByChildIdxAndBookIdx(childIdx, bookIdx)
                .orElseThrow(() -> new NotFoundException("좋아요 기록을 찾을 수 없습니다."));

        // 좋아요/싫어요 삭제
        bookLikeRepository.delete(bookLike);
    }
}
