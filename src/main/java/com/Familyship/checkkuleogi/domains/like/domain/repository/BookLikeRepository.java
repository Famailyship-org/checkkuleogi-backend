package com.Familyship.checkkuleogi.domains.like.domain.repository;

import com.Familyship.checkkuleogi.domains.child.domain.Child;
import com.Familyship.checkkuleogi.domains.like.domain.BookLike;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookLikeRepository extends JpaRepository<BookLike, Long> {

    boolean existsByChildIdxAndBookIdx(Long childIdx, Long bookIdx);

    List<BookLike> findByChild(Child child);

    List<BookLike> findByChildAndLikedislike(Child child, boolean like);

    Optional<BookLike> findByChildIdxAndBookIdx(Long childIdx, Long bookIdx);
}
