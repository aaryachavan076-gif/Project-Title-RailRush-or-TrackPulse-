package com.railrush.springboot.dto;

import java.time.LocalTime;

/**
 * Data Transfer Object for Train Details.
 */
public class TrainDTO {
    private String trainNumber;
    private String trainName;
    private String sourceStation;
    private String destinationStation;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private int totalSeats;
    private int availableSeats;
    private double fareSleeper;
    private double fareAC;

    public TrainDTO() {}

    public TrainDTO(String trainNumber, String trainName, String sourceStation, String destinationStation,
                    LocalTime departureTime, LocalTime arrivalTime, int totalSeats, int availableSeats,
                    double fareSleeper, double fareAC) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.fareSleeper = fareSleeper;
        this.fareAC = fareAC;
    }

    public String getTrainNumber() { return trainNumber; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }

    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }

    public String getSourceStation() { return sourceStation; }
    public void setSourceStation(String sourceStation) { this.sourceStation = sourceStation; }

    public String getDestinationStation() { return destinationStation; }
    public void setDestinationStation(String destinationStation) { this.destinationStation = destinationStation; }

    public LocalTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalTime departureTime) { this.departureTime = departureTime; }

    public LocalTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalTime arrivalTime) { this.arrivalTime = arrivalTime; }

    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    public double getFareSleeper() { return fareSleeper; }
    public void setFareSleeper(double fareSleeper) { this.fareSleeper = fareSleeper; }

    public double getFareAC() { return fareAC; }
    public void setFareAC(double fareAC) { this.fareAC = fareAC; }
}
