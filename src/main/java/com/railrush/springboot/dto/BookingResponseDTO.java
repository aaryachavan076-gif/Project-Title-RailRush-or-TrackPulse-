package com.railrush.springboot.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Booking Confirmation Response (Exp 13).
 */
public class BookingResponseDTO {
    private String pnrNumber;
    private String status;
    private String trainNumber;
    private String trainName;
    private String passengerName;
    private String travelClass;
    private double totalFare;
    private String allocatedBerth;
    private LocalDateTime bookingTime;
    private String message;

    public BookingResponseDTO() {}

    public BookingResponseDTO(String pnrNumber, String status, String trainNumber, String trainName,
                              String passengerName, String travelClass, double totalFare,
                              String allocatedBerth, LocalDateTime bookingTime, String message) {
        this.pnrNumber = pnrNumber;
        this.status = status;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.passengerName = passengerName;
        this.travelClass = travelClass;
        this.totalFare = totalFare;
        this.allocatedBerth = allocatedBerth;
        this.bookingTime = bookingTime;
        this.message = message;
    }

    public String getPnrNumber() { return pnrNumber; }
    public void setPnrNumber(String pnrNumber) { this.pnrNumber = pnrNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTrainNumber() { return trainNumber; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }

    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }

    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

    public String getTravelClass() { return travelClass; }
    public void setTravelClass(String travelClass) { this.travelClass = travelClass; }

    public double getTotalFare() { return totalFare; }
    public void setTotalFare(double totalFare) { this.totalFare = totalFare; }

    public String getAllocatedBerth() { return allocatedBerth; }
    public void setAllocatedBerth(String allocatedBerth) { this.allocatedBerth = allocatedBerth; }

    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
