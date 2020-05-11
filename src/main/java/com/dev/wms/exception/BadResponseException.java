package com.dev.wms.exception;

public class BadResponseException extends RuntimeException {
    public BadResponseException(String message) {
        super(message);
    }
}
