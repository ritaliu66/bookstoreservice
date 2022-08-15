package com.epam.bookstoreservice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnmatchedIdException extends RuntimeException {
    private Integer errorCode;

    private String errorMsg;

    public UnmatchedIdException() {
        super();
        this.errorCode = MyErrorCode.UNMATCHED_ID;
        this.errorMsg = "The id in the BookDTO does not match the path variable id";
    }
}
