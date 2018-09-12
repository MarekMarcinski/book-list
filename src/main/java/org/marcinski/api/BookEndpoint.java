package org.marcinski.api;

import org.marcinski.model.Book;
import org.marcinski.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @PostMapping("/api/books")
    public ResponseEntity<?> save(@RequestBody Book book) {
        if (book.getId() == null) {
            Book bookToSave = bookRepository.save(book);
            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(bookToSave.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(book);
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
