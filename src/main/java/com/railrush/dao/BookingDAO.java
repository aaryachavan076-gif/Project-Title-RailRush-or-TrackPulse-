package com.railrush.dao;

import com.railrush.exception.SeatUnavailableException;
import com.railrush.model.Passenger;
import com.railrush.model.Ticket;

import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BookingDAO managing ACID compliant seat reservation transactions (Exp 04, 05).
 */
public class BookingDAO {

    private static final String DIGITS = "0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generatePNR() {
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            sb.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
        }
        return sb.toString();
    }

    public synchronized String bookTicket(Ticket ticket) throws Exception {
        Connection conn = null;
        PreparedStatement checkSeatsStmt = null;
        PreparedStatement updateSeatsStmt = null;
        PreparedStatement insertBookingStmt = null;
        PreparedStatement insertPassengerStmt = null;
        ResultSet rs = null;

        String pnr = generatePNR();
        ticket.setPnrNumber(pnr);

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Begin ACID Transaction

            // 1. Check seat availability
            String checkSeatsSql = "SELECT available_seats FROM trains WHERE train_number = ? FOR UPDATE";
            checkSeatsStmt = conn.prepareStatement(checkSeatsSql);
            checkSeatsStmt.setString(1, ticket.getTrainNumber());
            rs = checkSeatsStmt.executeQuery();

            if (!rs.next() || rs.getInt("available_seats") < ticket.getPassengers().size()) {
                throw new SeatUnavailableException("Insufficient seats available on train " + ticket.getTrainNumber());
            }

            int currentAvailable = rs.getInt("available_seats");
            int passengerCount = ticket.getPassengers().size();

            // 2. Decrement seat inventory
            String updateSeatsSql = "UPDATE trains SET available_seats = ? WHERE train_number = ?";
            updateSeatsStmt = conn.prepareStatement(updateSeatsSql);
            updateSeatsStmt.setInt(1, currentAvailable - passengerCount);
            updateSeatsStmt.setString(2, ticket.getTrainNumber());
            updateSeatsStmt.executeUpdate();

            // 3. Insert into bookings
            String insertBookingSql = "INSERT INTO bookings (pnr_number, user_id, train_number, journey_date, " +
                                      "travel_class, total_fare, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            insertBookingStmt = conn.prepareStatement(insertBookingSql);
            insertBookingStmt.setString(1, pnr);
            insertBookingStmt.setInt(2, ticket.getUserId());
            insertBookingStmt.setString(3, ticket.getTrainNumber());
            insertBookingStmt.setDate(4, Date.valueOf(ticket.getJourneyDate()));
            insertBookingStmt.setString(5, ticket.getTravelClass());
            insertBookingStmt.setDouble(6, ticket.getTotalFare());
            insertBookingStmt.setString(7, ticket.getStatus());
            insertBookingStmt.executeUpdate();

            // 4. Insert passengers
            String insertPassengerSql = "INSERT INTO passengers (pnr_number, name, age, gender, seat_number) " +
                                        "VALUES (?, ?, ?, ?, ?)";
            insertPassengerStmt = conn.prepareStatement(insertPassengerSql);
            int seatOffset = 1;
            for (Passenger p : ticket.getPassengers()) {
                insertPassengerStmt.setString(1, pnr);
                insertPassengerStmt.setString(2, p.getName());
                insertPassengerStmt.setInt(3, p.getAge());
                insertPassengerStmt.setString(4, p.getGender());
                insertPassengerStmt.setString(5, "S" + (currentAvailable - seatOffset++));
                insertPassengerStmt.addBatch();
            }
            insertPassengerStmt.executeBatch();

            conn.commit(); // Commit Transaction
            return pnr;

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on failure
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (checkSeatsStmt != null) checkSeatsStmt.close();
            if (updateSeatsStmt != null) updateSeatsStmt.close();
            if (insertBookingStmt != null) insertBookingStmt.close();
            if (insertPassengerStmt != null) insertPassengerStmt.close();
            if (conn != null) conn.setAutoCommit(true);
        }
    }

    public Ticket getTicketByPNR(String pnr) throws Exception {
        String sql = "SELECT b.*, t.train_name, t.source_station, t.destination_station " +
                     "FROM bookings b JOIN trains t ON b.train_number = t.train_number " +
                     "WHERE b.pnr_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pnr);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Ticket ticket = new Ticket();
                    ticket.setPnrNumber(rs.getString("pnr_number"));
                    ticket.setUserId(rs.getInt("user_id"));
                    ticket.setTrainNumber(rs.getString("train_number"));
                    ticket.setTrainName(rs.getString("train_name"));
                    ticket.setSourceStation(rs.getString("source_station"));
                    ticket.setDestinationStation(rs.getString("destination_station"));
                    ticket.setJourneyDate(rs.getDate("journey_date").toLocalDate());
                    ticket.setTravelClass(rs.getString("travel_class"));
                    ticket.setTotalFare(rs.getDouble("total_fare"));
                    ticket.setStatus(rs.getString("status"));

                    // Fetch passengers
                    ticket.setPassengers(getPassengersByPNR(pnr));
                    return ticket;
                }
            }
        }
        return null;
    }

    public List<Passenger> getPassengersByPNR(String pnr) throws Exception {
        List<Passenger> list = new ArrayList<>();
        String sql = "SELECT * FROM passengers WHERE pnr_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pnr);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Passenger(
                        rs.getInt("passenger_id"),
                        rs.getString("pnr_number"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("seat_number")
                    ));
                }
            }
        }
        return list;
    }
}
