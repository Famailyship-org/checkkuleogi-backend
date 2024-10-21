package com.Familyship.checkkuleogi.domains.child.domain;

import com.Familyship.checkkuleogi.domains.user.domain.SiteUser;
import com.Familyship.checkkuleogi.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Child extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_idx")
    private Long idx;

    @Column(name = "child_name", nullable = false)
    private String name;

    @Column(name = "child_age")
    private Integer age;

    @Column(name = "child_gender")
    private String gender;

    @Column(name = "child_birth")
    private LocalDate birthday;

    @Column(name = "child_mbti")
    private String mbti;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "user_idx")
    private SiteUser parent;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "child_mbti_id")
    private ChildMBTI childMBTI;

    public void updateChildMbtiInfo(String mbti, ChildMBTI childMBTI) {
        this.mbti = mbti;
        this.childMBTI = childMBTI;
    }

    public boolean isMBTINull(String mbti) {
        return this.mbti == null;
    }
}
