package com.Familyship.checkkuleogi.domains.like.service;

import com.Familyship.checkkuleogi.domains.book.domain.Book;
import com.Familyship.checkkuleogi.domains.book.domain.repository.BookRepository;
import com.Familyship.checkkuleogi.domains.child.domain.Child;
import com.Familyship.checkkuleogi.domains.child.domain.repository.ChildRepository;
import com.Familyship.checkkuleogi.domains.like.domain.repository.BookLikeRepository;
import com.Familyship.checkkuleogi.domains.like.domain.BookLike;

import com.Familyship.checkkuleogi.domains.like.dto.LikeDto;
import com.Familyship.checkkuleogi.domains.like.dto.LikeResponseDto;
import com.Familyship.checkkuleogi.domains.like.dto.LikeListResponseDto;

import com.Familyship.checkkuleogi.domains.like.exception.DuplicateLikeException;
import com.Familyship.checkkuleogi.global.domain.exception.NotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookLikeServiceImpl implements BookLikeService{
    private final BookLikeRepository bookLikeRepository;
    private final ChildRepository childRepository;
    private final BookRepository bookRepository;

    public BookLikeServiceImpl(BookLikeRepository bookLikeRepository, ChildRepository childRepository, BookRepository bookRepository) {
        this.bookLikeRepository = bookLikeRepository;
        this.childRepository = childRepository;
        this.bookRepository = bookRepository;
    }

    // 좋아요/싫어요 생성
    public LikeResponseDto createLike(LikeDto dto) {
        // 중복 검증
        if (bookLikeRepository.existsByChildIdxAndBookIdx(dto.getChildIdx(), dto.getBookIdx())) {
            throw new DuplicateLikeException("이미 좋아요/싫어요를 눌렀습니다.");
        }

        Child child = findChildById(dto.getChildIdx());
        Book book = findBookById(dto.getBookIdx());

        BookLike bookLike = BookLike.builder()
                .child(child)
                .book(book)
                .likedislike(dto.getLikedislike())
                .build();
        bookLikeRepository.save(bookLike);

        return new LikeResponseDto(
                bookLike.getChild().getIdx(),
                bookLike.getBook().getIdx(),
                bookLike.getLikedislike()
        );
    }

    // 좋아요/싫어요 전체 목록 조회
    public LikeListResponseDto getLikeDislike(Long childIdx) {
        Child child = findChildById(childIdx);
        List<BookLike> likedislike = bookLikeRepository.findByChild(child);

        if (likedislike.isEmpty()) {
            throw new NotFoundException("좋아요/싫어요 기록이 없습니다.");
        }

        List<LikeResponseDto> responseList = likedislike.stream()
                .map(ld -> new LikeResponseDto(ld.getChild().getIdx(), ld.getBook().getIdx(), ld.getLikedislike()))
                .collect(Collectors.toList());

        return new LikeListResponseDto(responseList);
    }

    //좋아요만
    public LikeListResponseDto getLikes(Long childIdx) {
        Child child = findChildById(childIdx);
        List<BookLike> likes = bookLikeRepository.findByChildAndLikedislike(child, true);

        if (likes.isEmpty()) {
            throw new NotFoundException("좋아요 기록이 없습니다.");
        }

        List<LikeResponseDto> responseList = likes.stream()
                .map(like -> new LikeResponseDto(like.getChild().getIdx(), like.getBook().getIdx(), like.getLikedislike()))
                .collect(Collectors.toList());

        return new LikeListResponseDto(responseList);
    }

    //싫어요만
    public LikeListResponseDto getDislikes(Long childIdx) {
        Child child = findChildById(childIdx);
        List<BookLike> likes = bookLikeRepository.findByChildAndLikedislike(child, false);

        if (likes.isEmpty()) {
            throw new NotFoundException("싫어요 기록이 없습니다.");
        }

        List<LikeResponseDto> responseList = likes.stream()
                .map(like -> new LikeResponseDto(like.getChild().getIdx(), like.getBook().getIdx(), like.getLikedislike()))
                .collect(Collectors.toList());

        return new LikeListResponseDto(responseList);
    }


    // 좋아요 -> 싫어요 or 싫어요 -> 좋아요
    public void updateLike(LikeDto dto) {
        BookLike existbookLike = findBookLikeByChildAndBook(dto.getChildIdx(), dto.getBookIdx());

        boolean newLikeDislike = !existbookLike.getLikedislike();
        existbookLike.updateLikedislike(newLikeDislike);

        bookLikeRepository.save(existbookLike);
    }

    // 좋아요/싫어요 삭제
    public void deleteLike(LikeDto dto) {
        BookLike bookLike = findBookLikeByChildAndBook(dto.getChildIdx(), dto.getBookIdx());
        bookLikeRepository.delete(bookLike);
    }

    // Child 조회
    private Child findChildById(Long childIdx) {
        return childRepository.findById(childIdx)
                .orElseThrow(() -> new NotFoundException("어린이를 찾을 수 없습니다."));
    }

    // Book 조회
    private Book findBookById(Long bookIdx) {
        return bookRepository.findById(bookIdx)
                .orElseThrow(() -> new NotFoundException("책을 찾을 수 없습니다."));
    }

    // BookLike 조회
    private BookLike findBookLikeByChildAndBook(Long childIdx, Long bookIdx) {
        return bookLikeRepository.findByChildIdxAndBookIdx(childIdx, bookIdx)
                .orElseThrow(() -> new NotFoundException("좋아요/싫어요 기록을 찾을 수 없습니다."));
    }
}
