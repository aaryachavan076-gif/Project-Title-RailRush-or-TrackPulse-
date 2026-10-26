package com.railrush.model;

import java.time.LocalTime;

/**
 * Train model demonstrating Classes, Objects, Constructor Overloading and Method Overloading (Exp 01, 02).
 */
public class Train {
    private String trainNumber;
    private String trainName;
    private String sourceStation;
    private String destinationStation;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private int totalSeats;
    private int availableSeats;
    private double fareSleeper;
    private double fareAc;

    public enum TrainCategory {
        INTERCITY,
        LOCAL
    }

    private TrainCategory category = TrainCategory.INTERCITY;
    private double localFareMultiplier = 0.6;

    public TrainCategory getCategory() {
        return category;
    }

    public void setCategory(TrainCategory category) {
        this.category = category;
    }

    public double getLocalFareMultiplier() {
        return localFareMultiplier;
    }

    public void setLocalFareMultiplier(double localFareMultiplier) {
        this.localFareMultiplier = localFareMultiplier;
    }

    // Overloaded Constructor 1: Default
    public Train() {
    }

    // Overloaded Constructor 2: Lightweight representation for quick search
    public Train(String trainNumber, String trainName) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
    }

    // Overloaded Constructor 3: Full fleet model
    public Train(String trainNumber, String trainName, String sourceStation, String destinationStation,
                 LocalTime departureTime, LocalTime arrivalTime, int totalSeats, int availableSeats,
                 double fareSleeper, double fareAc) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.fareSleeper = fareSleeper;
        this.fareAc = fareAc;
    }

    // Overloaded Method 1: Search match by train number
    public boolean isMatch(String queryTrainNumber) {
        return this.trainNumber != null && this.trainNumber.equalsIgnoreCase(queryTrainNumber);
    }

    // Overloaded Method 2: Search match by source and destination
    public boolean isMatch(String querySource, String queryDestination) {
        return this.sourceStation != null && this.destinationStation != null &&
               this.sourceStation.equalsIgnoreCase(querySource) &&
               this.destinationStation.equalsIgnoreCase(queryDestination);
    }

    // Getters and Setters
    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getSourceStation() {
        return sourceStation;
    }

    public void setSourceStation(String sourceStation) {
        this.sourceStation = sourceStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getFareSleeper() {
        return fareSleeper;
    }

    public void setFareSleeper(double fareSleeper) {
        this.fareSleeper = fareSleeper;
    }

    public double getFareAc() {
        return fareAc;
    }

    public void setFareAc(double fareAc) {
        this.fareAc = fareAc;
    }
}
