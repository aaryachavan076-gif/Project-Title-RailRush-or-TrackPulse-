package com.railrush.springboot.service;

import com.railrush.springboot.dto.TrainDTO;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Service Layer for Train Fleet Operations (Exp 12/13).
 */
@Service
public class TrainService {

    private final ConcurrentHashMap<String, TrainDTO> trainRegistry = new ConcurrentHashMap<>();

    public TrainService() {
        // Initialize default fleet
        initDefaultFleet();
    }

    private void initDefaultFleet() {
        trainRegistry.put("12051", new TrainDTO(
                "12051", "Jan Shatabdi Express", "CSMT Mumbai", "Madgaon (Goa)",
                LocalTime.of(5, 10), LocalTime.of(14, 30), 500, 342, 320.0, 1050.0
        ));
        trainRegistry.put("22221", new TrainDTO(
                "22221", "Rajdhani Express", "CSMT Mumbai", "Hazrat Nizamuddin",
                LocalTime.of(16, 0), LocalTime.of(9, 55), 450, 89, 650.0, 2450.0
        ));
        trainRegistry.put("12123", new TrainDTO(
                "12123", "Deccan Queen Express", "CSMT Mumbai", "Pune Junction",
                LocalTime.of(17, 10), LocalTime.of(20, 25), 600, 480, 120.0, 480.0
        ));
        trainRegistry.put("11019", new TrainDTO(
                "11019", "Konark Express", "CSMT Mumbai", "Bhubaneswar",
                LocalTime.of(14, 0), LocalTime.of(4, 15), 550, 120, 580.0, 1980.0
        ));
    }

    public List<TrainDTO> getAllTrains() {
        return new ArrayList<>(trainRegistry.values());
    }

    public Optional<TrainDTO> getTrainByNumber(String trainNumber) {
        return Optional.ofNullable(trainRegistry.get(trainNumber));
    }

    public List<TrainDTO> searchTrains(String source, String destination) {
        return trainRegistry.values().stream()
                .filter(t -> (source == null || source.isBlank() || t.getSourceStation().equalsIgnoreCase(source.trim())) &&
                             (destination == null || destination.isBlank() || t.getDestinationStation().equalsIgnoreCase(destination.trim())))
                .collect(Collectors.toList());
    }

    public synchronized boolean decrementSeat(String trainNumber) {
        TrainDTO train = trainRegistry.get(trainNumber);
        if (train != null && train.getAvailableSeats() > 0) {
            train.setAvailableSeats(train.getAvailableSeats() - 1);
            return true;
        }
        return false;
    }
}
