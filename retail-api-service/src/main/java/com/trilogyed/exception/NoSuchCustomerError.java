package com.trilogyed.exception;

public class NoSuchCustomerError extends Exception {
    public NoSuchCustomerError(String message) {
        super(message);
    }
}
