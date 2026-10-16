# 🚆 RailRush (TrackPulse)

> **Next-Generation Railway Ticket Reservation & Real-Time Train Tracking System**  
> *A Comprehensive Full-Stack Java (FSJ) Engineering Mini-Project*

[![GitHub repo](https://img.shields.io/badge/GitHub-Repository-blue?logo=github)](https://github.com/aaryachavan076-gif/Project-Title-RailRush-or-TrackPulse-)
[![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Servlet / JSP](https://img.shields.io/badge/Jakarta%20EE-Servlets%20%7C%20JSP-007396?logo=java&logoColor=white)](https://jakarta.ee/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/Database-MySQL%208.0-4479A1?logo=mysql&logoColor=white)](https://www.mysql.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## 📌 Table of Contents

- [Overview](#-overview)
- [Key Features](#-key-features)
- [Academic Curriculum Alignment](#-academic-curriculum-alignment-fsj-lab)
- [System Architecture](#-system-architecture)
- [Database Design](#-database-design-er--schema)
- [Project Directory Structure](#-project-directory-structure)
- [Technology Stack](#-technology-stack)
- [Getting Started](#-getting-started)
  - [Prerequisites](#prerequisites)
  - [Database Configuration](#database-configuration)
  - [Running on Eclipse / Tomcat](#running-on-eclipse--apache-tomcat)
- [Application Workflow & Use Cases](#-application-workflow--use-cases)
- [Future Roadmap](#-future-roadmap)
- [Author & Acknowledgments](#-author--acknowledgments)

---

## 📖 Overview

**RailRush (TrackPulse)** is a comprehensive railway ticketing and fleet tracking web application engineered to solve critical bottlenecks in railway reservation and journey monitoring. Drawing inspiration from modern transit portals such as **IRCTC**, the system bridges the gap between high-volume passenger ticket booking and real-time train movement telemetry.

Built using enterprise-grade **Java (OOP, Servlets, JSP, JDBC, and Spring Boot)** with a responsive web front-end, RailRush guarantees ACID-compliant transactions, role-based access control, automated PNR generation, and seamless schedule monitoring.

---

## ✨ Key Features

### 🎫 Passenger Portal (RailRush)
- **Train Search & Schedule Inquiry**: Query trains between source and destination stations filtered by date, class (1AC, 2AC, 3AC, Sleeper), and quota (General / Tatkal).
- **Seat Availability & Dynamic Booking**: Real-time quota validation to avoid overbooking.
- **PNR Generation & Status Tracking**: Unique 10-digit PNR tracking with real-time status (`CONFIRMED`, `RAC`, `WAITLISTED`, `CANCELLED`).
- **Cancellation & Refund Computation**: Automatic calculation of refund deductions and inventory recovery.
- **User Dashboard & Journey History**: Clean interface displaying past journeys, active e-tickets, and profile preferences.

### 🧭 Live Train Telemetry (TrackPulse)
- **Real-Time Station Timings**: Live lookup of scheduled vs. estimated arrival/departure times.
- **Platform Allocation Indicator**: Live platform numbers for terminal and intermediate stations.
- **Delay Analytics**: Status indicators showing on-time performance and estimated delay in minutes.

### 🛡️ Administration & Operations
- **Fleet & Route Management**: Admin privileges to add new trains, manage stops, configure coach layouts, and set distance-based tariffs.
- **Quota & Inventory Control**: Regulate Tatkal and General ticket allocations per coach.
- **Audit Logs & Passenger Manifests**: Generate chart status and passenger lists prior to departure.

---

## 🎓 Academic Curriculum Alignment (FSJ Lab)

**RailRush (TrackPulse)** is purposely structured to demonstrate core computer engineering competencies and syllabus experiments:

| Exp No. | Laboratory Syllabus Topic | Concrete Implementation in RailRush |
| :---: | :--- | :--- |
| **01** | **Classes & Objects** | Encapsulated domain entities: `Train`, `Station`, `Passenger`, `Ticket`, and `Payment` with private fields and getter/setter methods. |
| **02** | **Method & Constructor Overloading** | Overloaded `Train` constructors (basic lookup vs full route model); overloaded `searchTrain(String trainNo)` and `searchTrain(String src, String dest, LocalDate date)`. |
| **03** | **Inheritance & Custom Exception Handling** | Class hierarchy (`User` $\rightarrow$ `PassengerUser`, `AdminUser`); Custom robust exceptions like `SeatUnavailableException`, `InvalidPNRException`, and `BookingLimitExceededException`. |
| **04** | **Generic DAO & HTTP Servlets** | Generic abstraction `GenericDAO<T>` for CRUD database transactions; `HttpServlet` subclasses (`LoginServlet`, `BookTicketServlet`, `TrackServlet`) handling `doGet()` and `doPost()`. |
| **05** | **JSP Login & JDBC Database Validation** | Secure `login.jsp` authenticating against MySQL database using `PreparedStatement` to prevent SQL Injection attacks. |
| **06** | **Implicit & Explicit JSP Objects** | Session state management (`session` for passenger authentication, `request` for search result forward, `<jsp:useBean>` for dynamic ticket rendering). |
| **07** | **HTML5, CSS3, & JavaScript Validation** | Client-side input validation via `validation.js` verifying station selection, email regex patterns, passenger age constraints, and PNR lengths. |
| **10–13** | **React & Spring Boot REST APIs** *(Modern Extension)* | Modular REST API endpoints (`/api/v1/trains`, `/api/v1/pnr/{id}`) enabling scalable microservices and Single Page Application (SPA) integration. |

---

## 🏗️ System Architecture

The application adopts a decoupled **3-Tier Model-View-Controller (MVC)** architectural standard:

```mermaid
flowchart TD
    subgraph Client ["Client Presentation Layer (View)"]
        UI["HTML5 / CSS3 / JavaScript / React"]
        JSP["JSP Dynamic Views (login.jsp, dashboard.jsp, pnr_status.jsp)"]
    end

    subgraph Controller ["Controller & Business Logic Layer"]
        SERVLET["Java Servlets / Spring Boot REST Controllers"]
        SESSION["HttpSession & State Management"]
        SERVICE["BookingEngine & TrackPulse Service"]
        EXCEPT["Custom Exception Handler"]
    end

    subgraph DataAccess ["Data Access Layer (DAO)"]
        DAO["GenericDAO<T> & TrainDAO / BookingDAO"]
        JDBC["JDBC Connection Pool / PreparedStatement"]
    end

    subgraph Database ["Persistence Layer"]
        MYSQL[("MySQL Relational Database")]
    end

    UI -->|HTTP Requests / Form Data| SERVLET
    JSP -->|Form Submissions| SERVLET
    SERVLET --> SESSION
    SERVLET --> SERVICE
    SERVICE --> EXCEPT
    SERVICE --> DAO
    DAO --> JDBC
    JDBC -->|SQL Queries| MYSQL
    MYSQL -->|ResultSets| JDBC
    JDBC --> DAO
    DAO --> SERVICE
    SERVICE -->|Data Attributes| JSP
    SERVICE -->|JSON Response| UI
```

---

## 🗄️ Database Design (ER & Schema)

### Relational Schema (`schema.sql`)

```sql
-- Database Initialization
CREATE DATABASE IF NOT EXISTS railrush_db;
USE railrush_db;

-- 1. Users Table (Role-Based Access)
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

-- Sample Seed Data
INSERT INTO users (full_name, email, password_hash, role, phone_number) VALUES
('System Administrator', 'admin@railrush.in', 'admin@123', 'ADMIN', '9876543210'),
('Aarya Chavan', 'aarya@example.com', 'user@123', 'PASSENGER', '9812345678');

INSERT INTO trains (train_number, train_name, source_station, destination_station, departure_time, arrival_time, fare_sleeper, fare_ac) VALUES
('12051', 'Jan Shatabdi Express', 'CSMT Mumbai', 'Madgaon (Goa)', '05:10:00', '14:30:00', 320.00, 1050.00),
('22221', 'Rajdhani Express', 'CSMT Mumbai', 'Hazrat Nizamuddin', '16:00:00', '09:55:00', 650.00, 2450.00),
('12123', 'Deccan Queen', 'CSMT Mumbai', 'Pune Junction', '17:10:00', '20:25:00', 120.00, 480.00);
```

---

## 📁 Project Directory Structure

```text
RailRush-TrackPulse/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/railrush/
│       │       ├── model/
│       │       │   ├── User.java                # Base user model
│       │       │   ├── PassengerUser.java       # User inheritance (Exp 03)
│       │       │   ├── Train.java               # Train model & constructor overloading (Exp 02)
│       │       │   ├── Ticket.java              # Ticket details & fare computation
│       │       │   └── LiveStatus.java          # TrackPulse telemetry model
│       │       ├── exception/
│       │       │   ├── SeatUnavailableException.java   # Custom exception (Exp 03)
│       │       │   ├── InvalidPNRException.java
│       │       │   └── BookingFailedException.java
│       │       ├── dao/
│       │       │   ├── GenericDAO.java          # Generic database interface (Exp 04)
│       │       │   ├── DBConnection.java        # JDBC connection manager
│       │       │   ├── TrainDAO.java            # Train inventory CRUD
│       │       │   └── BookingDAO.java          # Transactional booking persistence
│       │       └── servlet/
│       │           ├── LoginServlet.java        # Authentication servlet (Exp 04/05)
│       │           ├── SearchTrainServlet.java  # Search processing
│       │           ├── BookTicketServlet.java   # ACID booking & PNR generator
│       │           ├── TrackPulseServlet.java   # Live delay tracking endpoint
│       │           └── LogoutServlet.java       # Session invalidator
│       └── webapp/
│           ├── WEB-INF/
│           │   └── web.xml                      # Deployment descriptor
│           ├── css/
│           │   ├── style.css                    # Main design system
│           │   └── dashboard.css                # Passenger dashboard styling
│           ├── js/
│           │   ├── validation.js                # Client-side form validation (Exp 07)
│           │   └── live-track.js                # AJAX polling for live status
│           ├── index.jsp                        # Homepage & quick search
│           ├── login.jsp                        # Authentication page (Exp 05)
│           ├── register.jsp                     # Passenger account registration
│           ├── dashboard.jsp                    # Dynamic session dashboard (Exp 06)
│           ├── search_results.jsp               # Train availability list
│           ├── booking.jsp                      # Passenger details form
│           ├── ticket_confirmation.jsp          # PNR print view (<jsp:useBean>)
│           └── track_pulse.jsp                  # Real-time train tracking portal
├── database/
│   └── schema.sql                               # Complete MySQL database DDL/DML script
├── docs/
│   ├── Architecture_Diagram.png
│   └── Test_Cases.pdf
├── pom.xml                                      # Maven build file (or build.gradle)
└── README.md                                    # Project documentation
```

---

## 💻 Technology Stack

| Domain | Technology / Library | Description |
| :--- | :--- | :--- |
| **Programming Language** | Java (JDK 17 / 21) | Core object-oriented business logic and threading |
| **Server Engine** | Jakarta Servlets 5.0+ / JSP 3.0 | HTTP Request/Response dispatching & session handling |
| **Web Server** | Apache Tomcat 10.1+ | Enterprise servlet container |
| **Database** | MySQL Server 8.0+ | Relational data store for tickets, trains, and telemetry |
| **Database Connector** | `mysql-connector-j 8.x` | JDBC driver for type-safe database queries |
| **Front-End Design** | HTML5, CSS3, JavaScript (ES6) | Responsive UI with real-time DOM validation |
| **Build & Tools** | Apache Maven / Eclipse IDE / VS Code | Dependency management and application packaging |

---

## 🚀 Getting Started

### Prerequisites

Ensure you have the following installed on your machine:
1. **Java Development Kit (JDK 17 or higher)**: [Download JDK](https://www.oracle.com/java/technologies/downloads/)
2. **Apache Tomcat 10.x / 9.x**: [Download Tomcat](https://tomcat.apache.org/)
3. **MySQL Server & Workbench**: [Download MySQL](https://dev.mysql.com/downloads/installer/)
4. **IDE**: Eclipse IDE for Enterprise Java and Web Developers, or IntelliJ IDEA / VS Code.

---

### Database Configuration

1. Launch **MySQL Workbench** or your MySQL command-line client.
2. Execute the setup script provided in [`database/schema.sql`](#database-design-er--schema):
   ```sql
   source /path/to/database/schema.sql;
   ```
3. Update database credentials in `com/railrush/dao/DBConnection.java`:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/railrush_db";
   private static final String USER = "root";
   private static final String PASSWORD = "your_mysql_password";
   ```

---

### Running on Eclipse / Apache Tomcat

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/aaryachavan076-gif/Project-Title-RailRush-or-TrackPulse-.git
   ```
2. **Import into Eclipse**:
   - Open Eclipse $\rightarrow$ Select `File` > `Import...`
   - Select `Existing Projects into Workspace` or `Maven` > `Existing Maven Projects`.
   - Browse to the project folder and click `Finish`.
3. **Configure Apache Tomcat**:
   - In the `Servers` tab, click `New` > `Server` $\rightarrow$ Choose `Apache` > `Tomcat v10.1 Server`.
   - Point to your local Tomcat installation directory.
   - Right-click the server and choose `Add and Remove...` $\rightarrow$ Add `RailRush-TrackPulse` to configured resources.
4. **Deploy & Run**:
   - Click **Start Server** (or right-click `index.jsp` $\rightarrow$ `Run As` > `Run on Server`).
   - Open your browser and navigate to:
     ```text
     http://localhost:8080/RailRush-TrackPulse/
     ```

---

## 🔄 Application Workflow & Use Cases

```mermaid
sequenceDiagram
    autonumber
    actor Passenger as Passenger
    participant UI as Browser (JSP/JS)
    participant Servlet as BookTicketServlet
    participant DAO as BookingDAO
    participant DB as MySQL Database

    Passenger->>UI: Selects Train, Class & Enters Passenger Details
    UI->>UI: Client-Side Form Validation (validation.js)
    UI->>Servlet: HTTP POST /BookTicketServlet
    Servlet->>DAO: Check Seat Availability
    DAO->>DB: SELECT available_seats FROM trains WHERE train_number = ?
    DB-->>DAO: Available Seats Count

    alt Seats Not Available
        DAO-->>Servlet: Throw SeatUnavailableException
        Servlet-->>UI: Redirect with error: "Selected class is fully booked"
    else Seats Available
        Servlet->>DAO: Decrement Seat Count & Save Ticket
        DAO->>DB: INSERT INTO bookings, passengers; UPDATE trains
        DB-->>DAO: Transaction Committed
        DAO-->>Servlet: Return Generated 10-Digit PNR
        Servlet-->>UI: Forward to ticket_confirmation.jsp with Ticket Bean
        UI-->>Passenger: Render Downloadable / Printable E-Ticket
    end
```

---

## 🔮 Future Roadmap

- [ ] **AI-Powered Tatkal Waitlist Predictor**: Machine learning model estimating waitlist clearance probability.
- [ ] **Real-Time GPS Train Telemetry**: Integration with OpenRailwayMap / GPS hardware feeds.
- [ ] **Automated Payment Gateway**: Integration with Razorpay / UPI sandbox for instant payments.
- [ ] **SMS / WhatsApp Ticket Dispatch**: Automated alerts using Twilio / WhatsApp Business Cloud API.
- [ ] **Mobile App**: Cross-platform Flutter / React Native companion app connecting to the Spring Boot REST endpoints.

---

## 👨‍💻 Author & Acknowledgments

- **Developer**: [Aarya Chavan](https://github.com/aaryachavan076-gif)
- **Course**: Full Stack Java (FSJ) Programming Lab
- **Repository**: [Project-Title-RailRush-or-TrackPulse-](https://github.com/aaryachavan076-gif/Project-Title-RailRush-or-TrackPulse-)

---

<div align="center">
  <sub>Built with ❤️ using Java, Jakarta Servlets, JSP, MySQL, and modern web standards.</sub>
</div>
