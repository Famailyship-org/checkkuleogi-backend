package com.Familyship.checkkuleogi.domains.child.domain;

import com.Familyship.checkkuleogi.global.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import com.Familyship.checkkuleogi.domains.user.domain.SiteUser;

@Entity
@Getter
public class Child extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_idx")
    private Long idx;

    @Column(name = "child_name", nullable = false)
    private String name;

    @Column(name = "child_age")
    private int age;

    @Column(name = "child_gender")
    private String gender;

    @Column(name = "child_mbti")
    private String mbti;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "parent_id", referencedColumnName = "user_idx")
//    private SiteUser parent;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "user_idx")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // 부모 쪽도 적용
    private SiteUser parent;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "child_mbti_id") // 외래 키
    private ChildMBTI childMBTI;
}
