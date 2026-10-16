package com.railrush.model;

import java.time.LocalDateTime;

/**
 * LiveStatus model representing TrackPulse real-time train movement telemetry.
 */
public class LiveStatus {
    private int trackingId;
    private String trainNumber;
    private String trainName;
    private String currentStation;
    private String nextStation;
    private int delayMinutes;
    private LocalDateTime statusUpdatedAt;

    public LiveStatus() {
    }

    public LiveStatus(int trackingId, String trainNumber, String trainName, String currentStation,
                      String nextStation, int delayMinutes, LocalDateTime statusUpdatedAt) {
        this.trackingId = trackingId;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.currentStation = currentStation;
        this.nextStation = nextStation;
        this.delayMinutes = delayMinutes;
        this.statusUpdatedAt = statusUpdatedAt;
    }

    public int getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(int trackingId) {
        this.trackingId = trackingId;
    }

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

    public String getCurrentStation() {
        return currentStation;
    }

    public void setCurrentStation(String currentStation) {
        this.currentStation = currentStation;
    }

    public String getNextStation() {
        return nextStation;
    }

    public void setNextStation(String nextStation) {
        this.nextStation = nextStation;
    }

    public int getDelayMinutes() {
        return delayMinutes;
    }

    public void setDelayMinutes(int delayMinutes) {
        this.delayMinutes = delayMinutes;
    }

    public LocalDateTime getStatusUpdatedAt() {
        return statusUpdatedAt;
    }

    public void setStatusUpdatedAt(LocalDateTime statusUpdatedAt) {
        this.statusUpdatedAt = statusUpdatedAt;
    }
}
