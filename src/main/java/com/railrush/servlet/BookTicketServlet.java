package com.railrush.servlet;

import com.railrush.dao.BookingDAO;
import com.railrush.dao.TrainDAO;
import com.railrush.exception.SeatUnavailableException;
import com.railrush.model.Passenger;
import com.railrush.model.Ticket;
import com.railrush.model.Train;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;

/**
 * BookTicketServlet coordinating seat reservations, fare calculation, and PNR generation (Exp 04).
 */
@WebServlet("/BookTicketServlet")
public class BookTicketServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final BookingDAO bookingDAO = new BookingDAO();
    private final TrainDAO trainDAO = new TrainDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp?error=Please log in to book tickets");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        String trainNumber = request.getParameter("trainNumber");
        String journeyDateStr = request.getParameter("journeyDate");
        String travelClass = request.getParameter("travelClass");
        String passengerName = request.getParameter("passengerName");
        String passengerAgeStr = request.getParameter("passengerAge");
        String passengerGender = request.getParameter("passengerGender");

        try {
            Train train = trainDAO.findById(trainNumber);
            if (train == null) {
                response.sendRedirect("search_results.jsp?error=Train not found");
                return;
            }

            LocalDate journeyDate = LocalDate.parse(journeyDateStr);
            int age = Integer.parseInt(passengerAgeStr);

            double fare = "AC_TIER".equalsIgnoreCase(travelClass) || "AC".equalsIgnoreCase(travelClass)
                    ? train.getFareAc()
                    : train.getFareSleeper();
            // Apply local fare multiplier if this is a local train
            if (train.getCategory() == Train.TrainCategory.LOCAL) {
                fare = fare * train.getLocalFareMultiplier();
            }

            Ticket ticket = new Ticket();
            ticket.setUserId(userId);
            ticket.setTrainNumber(trainNumber);
            ticket.setJourneyDate(journeyDate);
            ticket.setTravelClass(travelClass);
            ticket.setTotalFare(fare);
            ticket.setStatus("CONFIRMED");
            ticket.addPassenger(new Passenger(passengerName, age, passengerGender));

            String pnr = bookingDAO.bookTicket(ticket);
            response.sendRedirect("ticket_confirmation.jsp?pnr=" + pnr);

        } catch (SeatUnavailableException e) {
            response.sendRedirect("booking.jsp?trainNumber=" + trainNumber + "&error=" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("booking.jsp?trainNumber=" + trainNumber + "&error=Booking failed: " + e.getMessage());
        }
    }
}
