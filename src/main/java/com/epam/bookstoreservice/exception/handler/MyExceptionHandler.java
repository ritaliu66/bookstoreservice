package com.epam.bookstoreservice.exception.handler;


import com.epam.bookstoreservice.dto.response.Result;
import com.epam.bookstoreservice.exception.InsufficientInventoryException;
import com.epam.bookstoreservice.exception.BookNotFoundException;
import com.epam.bookstoreservice.exception.PhoneNumberNotFoundException;
import com.epam.bookstoreservice.exception.UnmatchedIdException;
import com.epam.bookstoreservice.exception.WrongPhoneNumberOrPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

/**
 * Exception for custom exception
 */
@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler({
            BookNotFoundException.class,
            PhoneNumberNotFoundException.class
    })
    public ResponseEntity<Result<String>> handleNotFoundException(Throwable throwable) {
        return handlerException(throwable,HttpStatus.NOT_FOUND,HttpServletResponse.SC_NOT_FOUND);
    }

    @ExceptionHandler({
            InsufficientInventoryException.class,
            UnmatchedIdException.class,
            WrongPhoneNumberOrPasswordException.class

    })
    public ResponseEntity<Result<String>> handleBadRequestException(Throwable throwable) {
        return handlerException(throwable,HttpStatus.BAD_REQUEST,HttpServletResponse.SC_BAD_REQUEST);
    }

    private ResponseEntity<Result<String>> handlerException(Throwable throwable, HttpStatus status, Integer errorCode){
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON).body(Result.error(errorCode,throwable.toString()));
    }

}
