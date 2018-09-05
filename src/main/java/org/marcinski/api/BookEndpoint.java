package org.marcinski.api;

import org.marcinski.model.Book;
import org.marcinski.repository.BookRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookEndpoint {

    private BookRepository bookRepository;

    public BookEndpoint(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/api/books")
    public List<Book> getAll(){
        return bookRepository.findAll();
    }
}
