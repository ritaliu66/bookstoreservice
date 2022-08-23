package com.epam.bookstoreservice.exception;

import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletResponse;

/**
 * custom exception for phone number can not be found
 */
@Getter
@Setter
public class PhoneNumberNotFoundException extends RuntimeException {
    private final Integer errorCode;

    private final String errorMsg;

    public PhoneNumberNotFoundException() {
        super();
        this.errorCode = HttpServletResponse.SC_NOT_FOUND;
        this.errorMsg = "This phone number can not be found";
    }
}
