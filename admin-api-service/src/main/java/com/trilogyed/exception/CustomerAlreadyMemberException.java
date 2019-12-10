package com.trilogyed.exception;
public class CustomerAlreadyMemberException extends RuntimeException {
    public CustomerAlreadyMemberException(String message) {
        super(message);
    }
}