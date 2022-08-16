package com.epam.bookstoreservice.exception;


import com.epam.bookstoreservice.exception.errorcode.MyErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoSuchBookException extends RuntimeException {
    private Integer errorCode;

    private String errorMsg;

    public NoSuchBookException() {
        super();
        this.errorCode = MyErrorCode.NO_SUCH_BOOK;
        this.errorMsg = "This book is not available in the bookstore";
    }
}
