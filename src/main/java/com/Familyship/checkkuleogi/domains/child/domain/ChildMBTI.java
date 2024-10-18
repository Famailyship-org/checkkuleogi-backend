package com.Familyship.checkkuleogi.domains.child.domain;

import com.Familyship.checkkuleogi.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "child_mbti")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChildMBTI extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_mbti_idx")
    private Long idx;

    @Column(name = "mbti_e")
    private int mbtiE;

    @Column(name = "mbti_s")
    private int mbtiS;

    @Column(name = "mbti_t")
    private int mbtiT;

    @Column(name = "mbti_j")
    private int mbtiJ;
}