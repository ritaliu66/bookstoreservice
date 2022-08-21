package com.epam.bookstoreservice.exception.handler;


import com.epam.bookstoreservice.dto.response.Result;
import com.epam.bookstoreservice.exception.InsufficientInventoryException;
import com.epam.bookstoreservice.exception.BookNotFoundException;
import com.epam.bookstoreservice.exception.PhoneNumberNotFoundException;
import com.epam.bookstoreservice.exception.UnmatchedIdException;
import com.epam.bookstoreservice.exception.WrongPhoneNumberOrPassword;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(value = InsufficientInventoryException.class)
    public ResponseEntity<Result<String>> dealWithInsufficientInventoryException(InsufficientInventoryException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON).body(Result.error(ex.getErrorCode(), ex.getErrorMsg()));
    }

    @ExceptionHandler(value = BookNotFoundException.class)
    public ResponseEntity<Result<String>> dealWithNoSuchBookException(BookNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON).body(Result.error(ex.getErrorCode(), ex.getErrorMsg()));
    }

    @ExceptionHandler(value = UnmatchedIdException.class)
    public ResponseEntity<Result<String>> dealWithUnmatchedIdException(UnmatchedIdException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON).body(Result.error(ex.getErrorCode(), ex.getErrorMsg()));
    }

    @ExceptionHandler
    public ResponseEntity<Result<String>> dealWithPhoneNumberNotFoundException(PhoneNumberNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON).body(Result.error(ex.getErrorCode(), ex.getErrorMsg()));
    }

    @ExceptionHandler
    public ResponseEntity<Result<String>> dealWithWrongPhoneNumberOrPassword(WrongPhoneNumberOrPassword ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON).body(Result.error(ex.getErrorCode(), ex.getErrorMsg()));
    }

}
