package com.epam.bookstoreservice.exception;

import com.epam.bookstoreservice.exception.errorcode.MyErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneNumberNotFoundException extends RuntimeException{
    private Integer errorCode;

    private String errorMsg;

    public PhoneNumberNotFoundException() {
        super();
        this.errorCode = MyErrorCode.PHONE_NUMBER_NOT_FOUND;
        this.errorMsg = "This phone number can not be found";
    }
}
