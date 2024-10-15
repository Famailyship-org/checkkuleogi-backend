package com.Familyship.checkkuleogi.domains.user.domain;

import com.Familyship.checkkuleogi.domains.user.domain.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Table(name="user")
public class SiteUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long idx;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "user_id", nullable = false, unique = true)
    private String id;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "user_birth")
    private LocalDate birth;

    @Column(name = "user_gender")
    private String gender;

    @Column(name = "user_email", unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role role;

    @OneToMany(mappedBy = "parent")
    private List<Child> children;
}