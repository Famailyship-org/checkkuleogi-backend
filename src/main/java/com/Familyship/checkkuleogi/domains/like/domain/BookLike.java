package com.Familyship.checkkuleogi.domains.like.domain;

import com.Familyship.checkkuleogi.domains.book.domain.Book;
import com.Familyship.checkkuleogi.domains.child.domain.Child;
import com.Familyship.checkkuleogi.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "book_like")
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
    private boolean isLike;
}
