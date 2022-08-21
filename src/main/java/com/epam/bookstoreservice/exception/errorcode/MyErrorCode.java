package com.epam.bookstoreservice.exception.errorcode;

public class MyErrorCode {

    public static final Integer INSUFFICIENT_INVENTORY = 4001;
    public static final Integer BOOK_NOT_FOUND = 4002;
    public static final Integer UNMATCHED_ID = 4003;
    public static final Integer PHONE_NUMBER_NOT_FOUND = 4004;
    public static final Integer WRONG_PHONE_NUMBER_OR_PASSWORD = 4005;

    private MyErrorCode() {
    }
}
