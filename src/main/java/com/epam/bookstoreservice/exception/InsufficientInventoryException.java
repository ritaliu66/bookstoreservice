package com.epam.bookstoreservice.exception;


import com.epam.bookstoreservice.exception.errorcode.MyErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsufficientInventoryException extends RuntimeException {

    private Integer errorCode;

    private String errorMsg;

    public InsufficientInventoryException() {
        super();
        this.errorCode = MyErrorCode.INSUFFICIENT_INVENTORY;
        this.errorMsg = "This book is out of stock";
    }
}
