package com.Familyship.checkkuleogi.domains.child.domain;

import com.Familyship.checkkuleogi.global.domain.BaseEntity;
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
    private Integer mbtiE;

    @Column(name = "mbti_s")
    private Integer mbtiS;

    @Column(name = "mbti_t")
    private Integer mbtiT;

    @Column(name = "mbti_j")
    private Integer mbtiJ;

    @Column(name = "is_survey_result")
    private Boolean isSurveyResult;
}