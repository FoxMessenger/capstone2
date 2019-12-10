package com.trilogyed.exception;

public class LevelUpAccountDoesNotExistException extends RuntimeException {
    public LevelUpAccountDoesNotExistException(String message) {
        super(message);
    }
}
