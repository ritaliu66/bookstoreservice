package com.epam.bookstoreservice.exception;

import com.epam.bookstoreservice.exception.errorcode.MyErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnmatchedIdException extends RuntimeException {
    private final Integer errorCode;

    private final String errorMsg;

    public UnmatchedIdException() {
        super();
        this.errorCode = MyErrorCode.UNMATCHED_ID;
        this.errorMsg = "The id in the BookDTO does not match the path variable id";
    }
}
