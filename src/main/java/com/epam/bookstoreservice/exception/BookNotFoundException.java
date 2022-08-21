package com.epam.bookstoreservice.exception;


import com.epam.bookstoreservice.exception.errorcode.MyErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookNotFoundException extends RuntimeException {
    private final Integer errorCode;

    private final String errorMsg;

    public BookNotFoundException() {
        super();
        this.errorCode = MyErrorCode.BOOK_NOT_FOUND;
        this.errorMsg = "This book is not available in the bookstore";
    }
}
