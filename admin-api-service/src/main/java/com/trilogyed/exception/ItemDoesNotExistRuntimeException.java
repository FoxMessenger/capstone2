package com.trilogyed.exception;
public class ItemDoesNotExistRuntimeException extends RuntimeException {
    public ItemDoesNotExistRuntimeException(String message) {
        super(message);
    }
}