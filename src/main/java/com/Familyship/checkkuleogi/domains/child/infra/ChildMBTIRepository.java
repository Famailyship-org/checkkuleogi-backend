package com.Familyship.checkkuleogi.domains.child.infra;

import com.Familyship.checkkuleogi.domains.child.domain.ChildMBTI;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChildMBTIRepository extends JpaRepository<ChildMBTI, Long> {
    Optional<ChildMBTI> findByChildIdx(Long childIdx);
}
