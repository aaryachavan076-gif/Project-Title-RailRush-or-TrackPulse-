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
| **Exp 01: Programs on Classes & Objects** | `User.java`, `Train.java`, `Ticket.java`, `Passenger.java` | Strict OOP encapsulation with private fields, getters, setters, and constructors. |
| **Exp 02: Programs on Method & Constructor Overloading** | `Train.java`, `TrainDAO.java` | Multiple constructors for minimal lookup vs comprehensive tariffs; overloaded searchTrain methods. |
| **Exp 03: Programs on Inheritance & Exception Handling** | `PassengerUser extends User`, `AdminUser extends User`, `SeatUnavailableException`, `InvalidPNRException`, `BookingFailedException` | Type hierarchies and domain-specific exception handling. |
| **Exp 04: Generic & HTTP Servlet Implementation** | `GenericRailStatusServlet.java` (`GenericServlet.service()`), `GenericDAO<T, ID>`, `LoginServlet.java`, `BookTicketServlet.java` | Protocol-independent generic servlet alongside HTTP protocol dispatchers. |
| **Exp 05: JSP Login & JDBC Database Validation** | `login.jsp`, `LoginServlet.java`, `DBConnection.java`, `BookingDAO.java` | SQL injection prevention via PreparedStatement and batch query execution. |
| **Exp 06: Implicit & Explicit JSP Objects** | `dashboard.jsp`, `ticket_confirmation.jsp`, `experiments.jsp` | Use of `session`, `request`, `application`, `out`, and action tags `<jsp:useBean>`, `<jsp:getProperty>`. |
| **Exp 07: Responsive Website (HTML/CSS/JS)** | `index.jsp`, `css/style.css`, `js/validation.js` | Responsive transit web portal with CSS3 grid cards and responsive layouts. |
| **Exp 08: JS Email Validation ('@' & '.' check)** | `js/exp08_email_validation.js`, `js/validation.js` | Detects absence of '@' or '.' characters, displays alert error dialog, and refocuses input for re-entry. |
| **Exp 09: DOM 5-Second Background Color Rotation** | `js/exp09_dom_color.js`, `experiments.jsp` | DOM manipulation changing web background color automatically every 5 seconds (5000ms) with countdown timer. |
| **Exp 10: React Hooks 4 Color Buttons** | `js/exp10_react_color_hooks.js`, `experiments.jsp` | React functional component using useState hook with "Red", "Blue", "Green", "Yellow" buttons displaying dynamic selection text. |
| **Exp 11: React Single Page Website (SPA)** | `spa.jsp`, `js/exp11_react_spa.js` | Complete React SPA utilizing Hooks (useState, useEffect), simulated Router, Props passing, and State management. |
| **Exp 12: Spring Boot Monolithic Application** | `RailRushSpringBootApplication.java`, `RailRushMonolithController.java` | Server-Side Rendered (SSR) monolithic Spring Boot enterprise web application. |
| **Exp 13: Building RESTful APIs with Spring Boot** | `RailRushRestController.java`, `TrainService.java`, `BookingService.java` | Headless REST endpoints (`/api/v1/trains`, `/api/v1/bookings`, `/api/v1/pnr/{id}`, `/api/v1/telemetry/{id}`) with JSON serialization. |

---

## 5. Security & Transaction Integrity
- **ACID Transactions:** Seat reservation and passenger manifest writes run under `conn.setAutoCommit(false)` with explicit `conn.commit()` and `conn.rollback()` on exception.
- **Concurrency Control:** Pessimistic locking prevents overselling when multiple concurrent users request the final available seats.
- **Session Protection:** Sensitive actions require an active session identifier validated against `userId`.
