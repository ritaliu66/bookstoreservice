package com.epam.bookstoreservice.exception.handler;


import com.epam.bookstoreservice.exception.InsufficientInventoryException;
import com.epam.bookstoreservice.exception.NoSuchBookException;
import com.epam.bookstoreservice.exception.PhoneNumberNotFoundException;
import com.epam.bookstoreservice.exception.UnmatchedIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(value = InsufficientInventoryException.class)
    public ResponseEntity<Map> dealWithInsufficientInventoryException(InsufficientInventoryException ex) {
        Map<Integer, String> result = new HashMap<>();
        result.put(ex.getErrorCode(),ex.getErrorMsg());
        return new ResponseEntity(result,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NoSuchBookException.class)
    public ResponseEntity<Map> dealWithNoSuchBookException(NoSuchBookException ex){
        Map<Integer, String> result = new HashMap<>();
        result.put(ex.getErrorCode(),ex.getErrorMsg());
        return new ResponseEntity(result,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UnmatchedIdException.class)
    public ResponseEntity<Map> dealWithUnmatchedIdException(UnmatchedIdException ex){
        Map<Integer, String> result = new HashMap<>();
        result.put(ex.getErrorCode(),ex.getErrorMsg());
        return new ResponseEntity(result,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map> dealWithPhoneNumberNotFoundException(PhoneNumberNotFoundException  ex){
        Map<Integer, String> result = new HashMap<>();
        result.put(ex.getErrorCode(),ex.getErrorMsg());
        return new ResponseEntity(result,HttpStatus.BAD_REQUEST);
    }
}
