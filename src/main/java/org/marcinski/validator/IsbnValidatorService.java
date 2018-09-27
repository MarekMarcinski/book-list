package org.marcinski.validator;

import org.marcinski.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class IsbnValidatorService {

    private Validator validator;

    @Autowired
    public IsbnValidatorService(Validator validator) {
        this.validator = validator;
    }

    public boolean verifyIsbn(Book book){
        Set<ConstraintViolation<Book>> errors = validator.validate(book);
        if (!errors.isEmpty()){
            errors.forEach(err -> System.out.println(err.getMessage()));
            return false;
        }else {
            return true;
        }
    }
}
