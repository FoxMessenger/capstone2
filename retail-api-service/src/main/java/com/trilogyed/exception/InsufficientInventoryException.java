package com.trilogyed.exception;

public class InsufficientInventoryException extends Exception{

    public InsufficientInventoryException(String message) {
        super(message);
    }
}
