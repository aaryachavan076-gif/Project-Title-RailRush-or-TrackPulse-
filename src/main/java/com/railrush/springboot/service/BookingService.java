package com.railrush.springboot.service;

import com.railrush.exception.SeatUnavailableException;
import com.railrush.springboot.dto.BookingRequestDTO;
import com.railrush.springboot.dto.BookingResponseDTO;
import com.railrush.springboot.dto.TrainDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service Layer for Ticket Bookings and PNR Management (Exp 12/13).
 */
@Service
public class BookingService {

    private final TrainService trainService;
    private final Map<String, BookingResponseDTO> bookingLedger = new ConcurrentHashMap<>();
    private final Random random = new Random();

    public BookingService(TrainService trainService) {
        this.trainService = trainService;
        initSamplePNR();
    }

    private void initSamplePNR() {
        bookingLedger.put("RR84920194", new BookingResponseDTO(
                "RR84920194", "CONFIRMED", "12051", "Jan Shatabdi Express",
                "Aarya Chavan", "AC_3_TIER", 1050.0, "Coach B2 - Berth 31 (Lower)",
                LocalDateTime.now().minusDays(1), "Booking confirmed via RailRush Spring Boot System"
        ));
    }

    public synchronized BookingResponseDTO processBooking(BookingRequestDTO request) throws SeatUnavailableException {
        Optional<TrainDTO> trainOpt = trainService.getTrainByNumber(request.getTrainNumber());
        if (trainOpt.isEmpty()) {
            throw new IllegalArgumentException("Train number " + request.getTrainNumber() + " not found!");
        }

        TrainDTO train = trainOpt.get();
        if (train.getAvailableSeats() <= 0) {
            throw new SeatUnavailableException("No seats available on train " + train.getTrainNumber());
        }

        // Decrement seat
        trainService.decrementSeat(train.getTrainNumber());

        // Generate 10-character PNR
        String pnr = "RR" + (10000000 + random.nextInt(90000000));
        double fare = "SLEEPER".equalsIgnoreCase(request.getTravelClass()) ? train.getFareSleeper() : train.getFareAC();
        String berth = ("SLEEPER".equalsIgnoreCase(request.getTravelClass()) ? "Coach S3 - Berth " : "Coach B1 - Berth ") + (random.nextInt(72) + 1);

        BookingResponseDTO response = new BookingResponseDTO(
                pnr,
                "CONFIRMED",
                train.getTrainNumber(),
                train.getTrainName(),
                request.getPassengerName(),
                request.getTravelClass(),
                fare,
                berth,
                LocalDateTime.now(),
                "Ticket reserved successfully under ACID transaction model."
        );

        bookingLedger.put(pnr, response);
        return response;
    }

    public Optional<BookingResponseDTO> getBookingByPNR(String pnr) {
        return Optional.ofNullable(bookingLedger.get(pnr));
    }

    public Map<String, BookingResponseDTO> getAllBookings() {
        return bookingLedger;
    }
}
