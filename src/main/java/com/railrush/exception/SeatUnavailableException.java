package com.railrush.exception;

/**
 * Custom Exception thrown when booking is attempted on a train with zero available seats (Exp 03).
 */
public class SeatUnavailableException extends Exception {
    private static final long serialVersionUID = 1L;

    public SeatUnavailableException(String message) {
        super(message);
    }

    public SeatUnavailableException(String trainNumber, String travelClass) {
        super("No seats available on Train " + trainNumber + " for class " + travelClass);
    }
}
