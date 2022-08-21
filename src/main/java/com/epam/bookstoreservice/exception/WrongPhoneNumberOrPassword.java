package com.epam.bookstoreservice.exception;

import com.epam.bookstoreservice.exception.errorcode.MyErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WrongPhoneNumberOrPassword extends RuntimeException {

    private final Integer errorCode;

    private final String errorMsg;

    public WrongPhoneNumberOrPassword() {
        super();
        this.errorCode = MyErrorCode.WRONG_PHONE_NUMBER_OR_PASSWORD;
        this.errorMsg = "login failed, wrong user name or password";
    }
}
