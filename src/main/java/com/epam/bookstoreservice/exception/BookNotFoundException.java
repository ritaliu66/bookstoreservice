package com.epam.bookstoreservice.exception;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.servlet.http.HttpServletResponse;

/**
 * custom exception for book can not be found
 */
@Getter
@Setter
@ToString
public class BookNotFoundException extends RuntimeException {
    private final Integer errorCode;

    private final String errorMsg;

    public BookNotFoundException() {
        super();
        this.errorCode = HttpServletResponse.SC_NOT_FOUND;
        this.errorMsg = "This book can not be found in the bookstore";
    }
}
