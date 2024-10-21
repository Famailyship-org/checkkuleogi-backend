package com.Familyship.checkkuleogi.domains.book.domain.repository;

import com.Familyship.checkkuleogi.domains.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
