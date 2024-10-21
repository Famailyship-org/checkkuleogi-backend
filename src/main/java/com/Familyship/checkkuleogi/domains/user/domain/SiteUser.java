package com.Familyship.checkkuleogi.domains.user.domain;

import com.Familyship.checkkuleogi.domains.child.domain.Child;
import com.Familyship.checkkuleogi.domains.user.domain.enums.Role;
import com.Familyship.checkkuleogi.global.domain.BaseEntity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;

import java.util.Collection;
import java.util.List;


@Builder
@Entity
@Getter
@Table(name="user")
@AllArgsConstructor
@NoArgsConstructor
public class SiteUser extends BaseEntity implements UserDetails {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}