package com.Familyship.checkkuleogi.domains.user.domain.repository;

import com.Familyship.checkkuleogi.domains.user.domain.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    Optional<SiteUser> findById(String id);
}
