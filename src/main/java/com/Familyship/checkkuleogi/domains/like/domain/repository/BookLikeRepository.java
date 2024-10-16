package com.Familyship.checkkuleogi.domains.like.domain.repository;

import com.Familyship.checkkuleogi.domains.like.domain.BookLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookLikeRepository extends JpaRepository<BookLike, Long> {
    boolean existsByChildIdxAndBookIdx(Long childIdx, Long bookIdx);
}
