package com.Familyship.checkkuleogi.domains.book.domain;

import com.Familyship.checkkuleogi.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "book_mbti")
public class BookMBTI extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_mbti_idx")
    private Long idx;

    @Column(name = "mbti_e")
    private int mbtiE;

    @Column(name = "mbti_s")
    private int mbtiS;

    @Column(name = "mbti_t")
    private int mbtiT;

    @Column(name = "mbti_j")
    private int mbtiJ;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "book_idx", referencedColumnName = "book_idx")
    @Column(name = "book_idx")
    private Long bookIdx;
}
