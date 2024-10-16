package com.Familyship.checkkuleogi.domains.like.domain;

import com.Familyship.checkkuleogi.domains.book.domain.Book;
import com.Familyship.checkkuleogi.domains.child.domain.Child;
import com.Familyship.checkkuleogi.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

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
    private boolean likedislike;

    @Builder
    public BookLike(Child child, Book book, boolean likedislike) {
        this.child = child;
        this.book = book;
        this.likedislike = likedislike;
    }

    //필요 시 Setter사용
    public void setLikedislike(boolean likedislike) {
        this.likedislike = likedislike;
    }
}
