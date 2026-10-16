package com.railrush.dao;

import com.railrush.model.Train;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TrainDAO implementing GenericDAO for Train persistence (Exp 04, 05).
 */
public class TrainDAO implements GenericDAO<Train, String> {

    @Override
    public void save(Train train) throws Exception {
        String sql = "INSERT INTO trains (train_number, train_name, source_station, destination_station, " +
                     "departure_time, arrival_time, total_seats, available_seats, fare_sleeper, fare_ac) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, train.getTrainNumber());
            ps.setString(2, train.getTrainName());
            ps.setString(3, train.getSourceStation());
            ps.setString(4, train.getDestinationStation());
            ps.setTime(5, Time.valueOf(train.getDepartureTime()));
            ps.setTime(6, Time.valueOf(train.getArrivalTime()));
            ps.setInt(7, train.getTotalSeats());
            ps.setInt(8, train.getAvailableSeats());
            ps.setDouble(9, train.getFareSleeper());
            ps.setDouble(10, train.getFareAc());
            ps.executeUpdate();
        }
    }

    @Override
    public Train findById(String trainNumber) throws Exception {
        String sql = "SELECT * FROM trains WHERE train_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trainNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Train> findAll() throws Exception {
        List<Train> list = new ArrayList<>();
        String sql = "SELECT * FROM trains ORDER BY departure_time ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public List<Train> search(String source, String destination) throws Exception {
        List<Train> list = new ArrayList<>();
        String sql = "SELECT * FROM trains WHERE LOWER(source_station) LIKE LOWER(?) " +
                     "AND LOWER(destination_station) LIKE LOWER(?) ORDER BY departure_time ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + source + "%");
            ps.setString(2, "%" + destination + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    @Override
    public void update(Train train) throws Exception {
        String sql = "UPDATE trains SET train_name = ?, available_seats = ?, fare_sleeper = ?, fare_ac = ? " +
                     "WHERE train_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, train.getTrainName());
            ps.setInt(2, train.getAvailableSeats());
            ps.setDouble(3, train.getFareSleeper());
            ps.setDouble(4, train.getFareAc());
            ps.setString(5, train.getTrainNumber());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteById(String trainNumber) throws Exception {
        String sql = "DELETE FROM trains WHERE train_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trainNumber);
            ps.executeUpdate();
        }
    }

    private Train mapRow(ResultSet rs) throws SQLException {
        Train t = new Train();
        t.setTrainNumber(rs.getString("train_number"));
        t.setTrainName(rs.getString("train_name"));
        t.setSourceStation(rs.getString("source_station"));
        t.setDestinationStation(rs.getString("destination_station"));
        t.setDepartureTime(rs.getTime("departure_time").toLocalTime());
        t.setArrivalTime(rs.getTime("arrival_time").toLocalTime());
        t.setTotalSeats(rs.getInt("total_seats"));
        t.setAvailableSeats(rs.getInt("available_seats"));
        t.setFareSleeper(rs.getDouble("fare_sleeper"));
        t.setFareAc(rs.getDouble("fare_ac"));
        return t;
    }
}
