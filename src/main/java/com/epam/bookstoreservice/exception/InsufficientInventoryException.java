package com.epam.bookstoreservice.exception;


import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletResponse;

/**
 *  custom exception for insufficient inventory
 */
@Getter
@Setter
public class InsufficientInventoryException extends RuntimeException {

    private final Integer errorCode;

    private final String errorMsg;

    public InsufficientInventoryException() {
        super();
        this.errorCode = HttpServletResponse.SC_BAD_REQUEST;
        this.errorMsg = "This book is out of stock";
    }
}
