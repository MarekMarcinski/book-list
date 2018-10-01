package org.marcinski.api;

import org.marcinski.model.Book;
import org.marcinski.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
        if (book.getId() == null && book.getIsbn() == null) {
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

    @GetMapping("/api/books/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id){
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()){
            return ResponseEntity.ok(book.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
