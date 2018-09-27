package org.marcinski.validator;

import org.marcinski.repository.BookRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueIsbnValidator implements ConstraintValidator<UniqueIsbn, String> {

    private BookRepository bookRepository;

    public UniqueIsbnValidator() {
    }

    public UniqueIsbnValidator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void initialize(UniqueIsbn isbn) {
    }

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        return isbn != null && !bookRepository.findByIsbn(isbn).isPresent();
    }
}
