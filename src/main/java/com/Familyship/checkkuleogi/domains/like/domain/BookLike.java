package com.Familyship.checkkuleogi.domains.like.domain;

import com.Familyship.checkkuleogi.domains.book.domain.Book;
import com.Familyship.checkkuleogi.domains.child.domain.Child;
import com.Familyship.checkkuleogi.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "book_like", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"child_idx", "book_idx"})
})
public class BookLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_idx")
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_idx", referencedColumnName = "child_idx")
    private Child child;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_idx", referencedColumnName = "book_idx")
    private Book book;

    @Column(name = "is_like")
    private Boolean likedislike;

    @Builder
    public BookLike(Child child, Book book, boolean likedislike) {
        this.child = child;
        this.book = book;
        this.likedislike = likedislike;
    }

    public void updateLikedislike(boolean likedislike) {
        this.likedislike = likedislike;
    }
}
