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
    private Integer mbtiE;

    @Column(name = "mbti_s")
    private Integer mbtiS;

    @Column(name = "mbti_t")
    private Integer mbtiT;

    @Column(name = "mbti_j")
    private Integer mbtiJ;
}
