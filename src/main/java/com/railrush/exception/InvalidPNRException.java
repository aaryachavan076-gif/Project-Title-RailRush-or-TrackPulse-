package com.railrush.exception;

/**
 * Custom Exception thrown when an invalid PNR is queried (Exp 03).
 */
public class InvalidPNRException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidPNRException(String message) {
        super(message);
    }
}
