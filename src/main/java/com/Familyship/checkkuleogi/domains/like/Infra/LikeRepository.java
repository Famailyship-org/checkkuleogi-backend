package com.Familyship.checkkuleogi.domains.like.Infra;

import com.Familyship.checkkuleogi.domains.like.domain.BookLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<BookLike, Long> {

    Optional<List<BookLike>> findByChildIdxAndIsDeleted(Long idx, Boolean isDeleted);
}
