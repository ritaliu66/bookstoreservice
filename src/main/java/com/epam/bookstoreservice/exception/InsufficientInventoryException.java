package com.epam.bookstoreservice.exception;


import com.epam.bookstoreservice.exception.errorcode.MyErrorCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InsufficientInventoryException extends RuntimeException {

    private final Integer errorCode;

    private final String errorMsg;

    public InsufficientInventoryException() {
        super();
        this.errorCode = MyErrorCode.INSUFFICIENT_INVENTORY;
        this.errorMsg = "This book is out of stock";
    }
}
