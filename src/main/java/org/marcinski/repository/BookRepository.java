package org.marcinski.repository;

import org.marcinski.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByIsbn(String isbn);
}
