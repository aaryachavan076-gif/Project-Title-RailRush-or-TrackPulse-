# RailRush (TrackPulse) - Technical Project Report

**Project Title:** RailRush: Real-Time Railway Reservation & Telemetry Tracking System  
**Repository:** [https://github.com/aaryachavan076-gif/Project-Title-RailRush-or-TrackPulse-](https://github.com/aaryachavan076-gif/Project-Title-RailRush-or-TrackPulse-)  
**Developer:** Aarya Chavan  
**Stack:** Full-Stack Java (Jakarta Servlets 6.0, JSP 3.1, JDBC, MySQL 8.0, HTML5/CSS3/JavaScript)

---

## 1. Executive Summary
RailRush is an enterprise-oriented web application designed to handle high-concurrency train ticket reservations and live movement telemetry (TrackPulse). It addresses core transit bottlenecks including race conditions during seat allocation, dynamic fare computation, instant PNR lifecycle tracking, and real-time station delay feeds.

---

## 2. System Architecture & Design Principles

### 3-Tier Layered Architecture
1. **Presentation Layer (JSP / CSS3 / ES6):**
   - Clean, responsive UI with zero external bulky frontend dependencies.
   - Client-side validation through `validation.js` ensuring input sanitization before hitting servlet controllers.
   - Dynamic view generation using JSP Expression Language (EL), Scriptlets, and Standard Action tags (`<jsp:useBean>`, `<jsp:getProperty>`).

2. **Controller & Service Layer (Jakarta Servlets):**
   - `SearchTrainServlet`: Performs parametric station lookup and schedule querying.
   - `BookTicketServlet`: Coordinates ACID-compliant multi-row transactional booking.
   - `LoginServlet` & `LogoutServlet`: Manages session state via `HttpSession`.
   - `TrackPulseServlet`: Exposes real-time station telemetry and delay analytics.

3. **Data Access Layer (JDBC & MySQL):**
   - `GenericDAO<T, ID>`: Implements generic repository patterns.
   - `DBConnection`: Thread-safe Singleton connection factory.
   - Row-level database locking (`SELECT ... FOR UPDATE`) prevents double booking.

---

## 3. Database Schema Specification

- **`users` Table:**
  Stores authentication credentials, user roles (`ADMIN` vs `PASSENGER`), and contact data.
- **`trains` Table:**
  Maintains fleet master data: train number, source, destination, departure/arrival schedules, and available seat quotas.
- **`bookings` Table:**
  Transactional header recording 10-digit PNR, travel class, total fare, and booking status.
- **`passengers` Table:**
  Individual passenger manifest records referencing PNR foreign key with allocated coach seat identifiers.
- **`live_tracking` Table:**
  Telemetry records mapping trains to current stations, next en-route junctions, and delay minutes.

---

## 4. Key Academic Experiment Alignments

| Lab Experiment | Implemented Component | Description |
|---|---|---|
| **Exp 01: Classes & Objects** | `User.java`, `Train.java`, `Ticket.java`, `Passenger.java` | Strict OOP encapsulation with private fields, getters, setters, and constructors. |
| **Exp 02: Method & Constructor Overloading** | `Train.java` | Multiple constructors for fast catalog caching vs comprehensive route models. |
| **Exp 03: Inheritance & Custom Exceptions** | `PassengerUser extends User`, `SeatUnavailableException`, `InvalidPNRException`, `BookingFailedException` | Type hierarchies and domain-specific exception handling. |
| **Exp 04: Generic DAO & Servlets** | `GenericDAO<T, ID>`, `TrainDAO.java`, `BookTicketServlet.java` | Generic interface abstraction and HTTP protocol dispatchers. |
| **Exp 05: JDBC PreparedStatement** | `DBConnection.java`, `LoginServlet.java`, `BookingDAO.java` | SQL injection prevention and batch query execution. |
| **Exp 06: Implicit & Explicit JSP Objects** | `dashboard.jsp`, `ticket_confirmation.jsp` | Use of `session`, `request`, `out`, and `<jsp:useBean>`. |
| **Exp 07: JavaScript Validation** | `validation.js` | Form validation for stations, emails, ages, and 10-digit PNRs. |

---

## 5. Security & Transaction Integrity
- **ACID Transactions:** Seat reservation and passenger manifest writes run under `conn.setAutoCommit(false)` with explicit `conn.commit()` and `conn.rollback()` on exception.
- **Concurrency Control:** Pessimistic locking prevents overselling when multiple concurrent users request the final available seats.
- **Session Protection:** Sensitive actions require an active session identifier validated against `userId`.
