package com.railrush.exception;

/**
 * Custom Exception thrown when ticket booking fails due to transaction or constraint errors (Exp 03).
 */
public class BookingFailedException extends Exception {
    private static final long serialVersionUID = 1L;

    public BookingFailedException(String message) {
        super(message);
    }

    public BookingFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
