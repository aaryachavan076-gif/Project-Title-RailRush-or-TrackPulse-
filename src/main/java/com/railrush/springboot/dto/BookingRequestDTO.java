package com.railrush.springboot.dto;

import java.time.LocalDate;

/**
 * Data Transfer Object for Ticket Booking Request (Exp 13).
 */
public class BookingRequestDTO {
    private int userId;
    private String trainNumber;
    private LocalDate journeyDate;
    private String travelClass;
    private String passengerName;
    private int passengerAge;
    private String passengerGender;

    public BookingRequestDTO() {}

    public BookingRequestDTO(int userId, String trainNumber, LocalDate journeyDate, String travelClass,
                             String passengerName, int passengerAge, String passengerGender) {
        this.userId = userId;
        this.trainNumber = trainNumber;
        this.journeyDate = journeyDate;
        this.travelClass = travelClass;
        this.passengerName = passengerName;
        this.passengerAge = passengerAge;
        this.passengerGender = passengerGender;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getTrainNumber() { return trainNumber; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }

    public LocalDate getJourneyDate() { return journeyDate; }
    public void setJourneyDate(LocalDate journeyDate) { this.journeyDate = journeyDate; }

    public String getTravelClass() { return travelClass; }
    public void setTravelClass(String travelClass) { this.travelClass = travelClass; }

    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

    public int getPassengerAge() { return passengerAge; }
    public void setPassengerAge(int passengerAge) { this.passengerAge = passengerAge; }

    public String getPassengerGender() { return passengerGender; }
    public void setPassengerGender(String passengerGender) { this.passengerGender = passengerGender; }
}
