-- ========================================================
-- Database Schema for RailRush (TrackPulse)
-- Full-Stack Java (FSJ) Mini Project
-- ========================================================

CREATE DATABASE IF NOT EXISTS railrush_db;
USE railrush_db;

-- 1. Users Table (Role-based access: Admin vs Passenger)
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('PASSENGER', 'ADMIN') DEFAULT 'PASSENGER',
    phone_number VARCHAR(15),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Trains Master Table
CREATE TABLE IF NOT EXISTS trains (
    train_number VARCHAR(10) PRIMARY KEY,
    train_name VARCHAR(100) NOT NULL,
    source_station VARCHAR(50) NOT NULL,
    destination_station VARCHAR(50) NOT NULL,
    departure_time TIME NOT NULL,
    arrival_time TIME NOT NULL,
    total_seats INT NOT NULL DEFAULT 500,
    available_seats INT NOT NULL DEFAULT 500,
    fare_sleeper DECIMAL(8,2) NOT NULL,
    fare_ac DECIMAL(8,2) NOT NULL
);

-- 3. Bookings Table
CREATE TABLE IF NOT EXISTS bookings (
    pnr_number VARCHAR(10) PRIMARY KEY,
    user_id INT NOT NULL,
    train_number VARCHAR(10) NOT NULL,
    journey_date DATE NOT NULL,
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    travel_class ENUM('SLEEPER', 'AC_3_TIER', 'AC_2_TIER', 'AC_FIRST') NOT NULL,
    total_fare DECIMAL(10,2) NOT NULL,
    status ENUM('CONFIRMED', 'WAITLISTED', 'CANCELLED') DEFAULT 'CONFIRMED',
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (train_number) REFERENCES trains(train_number) ON DELETE CASCADE
);

-- 4. Passengers Table
CREATE TABLE IF NOT EXISTS passengers (
    passenger_id INT AUTO_INCREMENT PRIMARY KEY,
    pnr_number VARCHAR(10) NOT NULL,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender ENUM('MALE', 'FEMALE', 'OTHER') NOT NULL,
    seat_number VARCHAR(10),
    FOREIGN KEY (pnr_number) REFERENCES bookings(pnr_number) ON DELETE CASCADE
);

-- 5. TrackPulse Live Status Table
CREATE TABLE IF NOT EXISTS live_tracking (
    tracking_id INT AUTO_INCREMENT PRIMARY KEY,
    train_number VARCHAR(10) NOT NULL,
    current_station VARCHAR(50) NOT NULL,
    next_station VARCHAR(50) NOT NULL,
    delay_minutes INT DEFAULT 0,
    status_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (train_number) REFERENCES trains(train_number) ON DELETE CASCADE
);

-- ========================================================
-- Seed Data
-- ========================================================

INSERT INTO users (full_name, email, password_hash, role, phone_number) VALUES
('System Administrator', 'admin@railrush.in', 'admin@123', 'ADMIN', '9876543210'),
('Aarya Chavan', 'aarya@example.com', 'user@123', 'PASSENGER', '9812345678');

INSERT INTO trains (train_number, train_name, source_station, destination_station, departure_time, arrival_time, fare_sleeper, fare_ac) VALUES
('12051', 'Jan Shatabdi Express', 'CSMT Mumbai', 'Madgaon (Goa)', '05:10:00', '14:30:00', 320.00, 1050.00),
('22221', 'Rajdhani Express', 'CSMT Mumbai', 'Hazrat Nizamuddin', '16:00:00', '09:55:00', 650.00, 2450.00),
('12123', 'Deccan Queen', 'CSMT Mumbai', 'Pune Junction', '17:10:00', '20:25:00', 120.00, 480.00);

INSERT INTO live_tracking (train_number, current_station, next_station, delay_minutes) VALUES
('12051', 'Thane', 'Panvel', 5),
('22221', 'Kalyan', 'Nashik Road', 0),
('12123', 'Dadar', 'Kalyan', 2);
