package com.epam.bookstoreservice.exception;

import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletResponse;

/**
 * custom exception for wrong phone number or password
 */
@Getter
@Setter
public class WrongPhoneNumberOrPasswordException extends RuntimeException {

    private final Integer errorCode;

    private final String errorMsg;

    public WrongPhoneNumberOrPasswordException() {
        super();
        this.errorCode = HttpServletResponse.SC_BAD_REQUEST;
        this.errorMsg = "login failed, wrong user name or password";
    }
}
