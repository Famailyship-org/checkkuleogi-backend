package com.Familyship.checkkuleogi.domains.child.infra;

import com.Familyship.checkkuleogi.domains.child.domain.Child;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child, Long> {

    Optional<Child> findByAndName(String name);
}
