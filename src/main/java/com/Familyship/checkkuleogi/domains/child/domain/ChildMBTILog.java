package com.Familyship.checkkuleogi.domains.child.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "child_mbti_log")
public class ChildMBTILog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_mbti_log_idx")
    private Long idx;

    @Column
    private Long childIdx;

    @Column(name = "mbti_e")
    private int mbtiE;

    @Column(name = "mbti_s")
    private int mbtiS;

    @Column(name = "mbti_t")
    private int mbtiT;

    @Column(name = "mbti_j")
    private int mbtiJ;

    @Column(name = "is_survey_result")
    private boolean isSurveyResult;
}