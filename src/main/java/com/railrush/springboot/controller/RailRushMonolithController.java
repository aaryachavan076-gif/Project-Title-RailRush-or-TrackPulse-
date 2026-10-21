package com.railrush.springboot.controller;

import com.railrush.springboot.service.BookingService;
import com.railrush.springboot.service.TrainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Experiment 12: Monolithic Application using Spring Boot.
 * Coordinates traditional Server-Side Rendering (SSR) MVC workflows,
 * managing session context, template data binding, and monolith navigation.
 */
@Controller
public class RailRushMonolithController {

    private final TrainService trainService;
    private final BookingService bookingService;

    public RailRushMonolithController(TrainService trainService, BookingService bookingService) {
        this.trainService = trainService;
        this.bookingService = bookingService;
    }

    @GetMapping("/spring")
    public String index(Model model) {
        model.addAttribute("title", "RailRush Spring Boot Monolith");
        model.addAttribute("trains", trainService.getAllTrains());
        model.addAttribute("systemTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        model.addAttribute("activeBookingsCount", bookingService.getAllBookings().size());
        return "index";
    }

    @GetMapping("/spring/trains")
    public String listTrains(
            @RequestParam(required = false) String source,
            @RequestParam(required = false) String destination,
            Model model) {
        if (source != null || destination != null) {
            model.addAttribute("trains", trainService.searchTrains(source, destination));
        } else {
            model.addAttribute("trains", trainService.getAllTrains());
        }
        return "search_results";
    }

    @GetMapping("/spring/pnr")
    public String pnrStatus(@RequestParam(required = false) String pnr, Model model) {
        if (pnr != null && !pnr.isBlank()) {
            bookingService.getBookingByPNR(pnr.trim())
                    .ifPresent(b -> model.addAttribute("booking", b));
        }
        return "ticket_confirmation";
    }
}
