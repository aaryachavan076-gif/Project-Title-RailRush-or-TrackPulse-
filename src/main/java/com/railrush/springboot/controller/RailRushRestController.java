package com.railrush.springboot.controller;

import com.railrush.exception.SeatUnavailableException;
import com.railrush.springboot.dto.BookingRequestDTO;
import com.railrush.springboot.dto.BookingResponseDTO;
import com.railrush.springboot.dto.TrainDTO;
import com.railrush.springboot.service.BookingService;
import com.railrush.springboot.service.TrainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Experiment 13: Program for Building RESTful APIs with Spring Boot.
 * Exposes full CRUD & domain endpoints for modern headless & SPA clients.
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class RailRushRestController {

    private final TrainService trainService;
    private final BookingService bookingService;

    public RailRushRestController(TrainService trainService, BookingService bookingService) {
        this.trainService = trainService;
        this.bookingService = bookingService;
    }

    /**
     * System Health & API Discovery Endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> status = new LinkedHashMap<>();
        status.put("service", "RailRush REST API Gateway");
        status.put("status", "UP");
        status.put("timestamp", LocalDateTime.now());
        status.put("version", "1.0.0 (Spring Boot 3.x / Java 26)");
        status.put("experiment", "Exp 13: Building RESTful APIs with Spring Boot");
        return ResponseEntity.ok(status);
    }

    /**
     * Retrieve all scheduled trains
     */
    @GetMapping("/trains")
    public ResponseEntity<List<TrainDTO>> getAllTrains() {
        return ResponseEntity.ok(trainService.getAllTrains());
    }

    /**
     * Retrieve specific train by number
     */
    @GetMapping("/trains/{trainNumber}")
    public ResponseEntity<?> getTrainByNumber(@PathVariable String trainNumber) {
        return trainService.getTrainByNumber(trainNumber)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Train number " + trainNumber + " not found.")));
    }

    /**
     * Parametric search for trains between source and destination
     */
    @GetMapping("/trains/search")
    public ResponseEntity<List<TrainDTO>> searchTrains(
            @RequestParam(required = false) String source,
            @RequestParam(required = false) String destination) {
        return ResponseEntity.ok(trainService.searchTrains(source, destination));
    }

    /**
     * ACID Reservation Endpoint: Creates ticket booking and returns generated 10-digit PNR
     */
    @PostMapping("/bookings")
    public ResponseEntity<?> createBooking(@RequestBody BookingRequestDTO request) {
        try {
            if (request.getTrainNumber() == null || request.getPassengerName() == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Train number and Passenger name are required."));
            }
            BookingResponseDTO response = bookingService.processBooking(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (SeatUnavailableException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage(), "status", "SEAT_UNAVAILABLE"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Retrieve booking status and details by 10-digit PNR
     */
    @GetMapping("/pnr/{pnr}")
    public ResponseEntity<?> getPNRStatus(@PathVariable String pnr) {
        return bookingService.getBookingByPNR(pnr)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "PNR " + pnr + " not found in database.", "pnr", pnr)));
    }

    /**
     * TrackPulse Live Movement Telemetry Endpoint
     */
    @GetMapping("/telemetry/{trainNumber}")
    public ResponseEntity<Map<String, Object>> getLiveTelemetry(@PathVariable String trainNumber) {
        Optional<TrainDTO> trainOpt = trainService.getTrainByNumber(trainNumber);
        Map<String, Object> telemetry = new LinkedHashMap<>();

        if (trainOpt.isPresent()) {
            TrainDTO train = trainOpt.get();
            telemetry.put("trainNumber", train.getTrainNumber());
            telemetry.put("trainName", train.getTrainName());
            telemetry.put("currentStation", "Ratnagiri Junction");
            telemetry.put("nextStation", "Madgaon (Goa)");
            telemetry.put("delayMinutes", 0);
            telemetry.put("status", "ON TIME");
            telemetry.put("platform", 2);
            telemetry.put("lastPing", LocalDateTime.now());
            return ResponseEntity.ok(telemetry);
        } else {
            telemetry.put("error", "Train " + trainNumber + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(telemetry);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "error", "Internal Server Error",
                "message", ex.getMessage(),
                "timestamp", LocalDateTime.now()
        ));
    }
}
