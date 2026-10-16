<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp?error=Please log in to continue booking");
        return;
    }
    String trainNumber = request.getParameter("trainNumber");
    String journeyDate = request.getParameter("journeyDate");
    if (journeyDate == null || journeyDate.isEmpty()) {
        journeyDate = "2026-10-25";
    }
    String error = request.getParameter("error");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Book Ticket - RailRush</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="js/validation.js"></script>
</head>
<body>
    <header>
        <div class="nav-container">
            <a href="index.jsp" class="logo">🚆 Rail<span>Rush</span></a>
            <nav>
                <a href="dashboard.jsp">Dashboard</a>
                <a href="track_pulse.jsp">TrackPulse</a>
            </nav>
        </div>
    </header>

    <div class="container" style="max-width: 600px;">
        <div class="card">
            <h2>Passenger Booking Form</h2>
            <p style="color: var(--text-muted); margin-bottom: 1.5rem;">
                Train Number: <strong><%= trainNumber %></strong> | Journey Date: <strong><%= journeyDate %></strong>
            </p>

            <% if (error != null) { %>
                <div class="alert alert-danger"><%= error %></div>
            <% } %>

            <form name="bookingForm" action="BookTicketServlet" method="post" onsubmit="return validateBookingForm()">
                <input type="hidden" name="trainNumber" value="<%= trainNumber %>">
                <input type="hidden" name="journeyDate" value="<%= journeyDate %>">

                <div class="form-group">
                    <label for="travelClass">Select Travel Class</label>
                    <select id="travelClass" name="travelClass" class="form-control" required>
                        <option value="SLEEPER">Sleeper Class (SL)</option>
                        <option value="AC_TIER">AC 3-Tier (3A)</option>
                        <option value="AC_2_TIER">AC 2-Tier (2A)</option>
                        <option value="AC_FIRST">AC First Class (1A)</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="passengerName">Passenger Full Name</label>
                    <input type="text" id="passengerName" name="passengerName" class="form-control" placeholder="Aarya Chavan" required>
                </div>

                <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 1rem;">
                    <div class="form-group">
                        <label for="passengerAge">Age</label>
                        <input type="number" id="passengerAge" name="passengerAge" class="form-control" min="1" max="120" placeholder="20" required>
                    </div>

                    <div class="form-group">
                        <label for="passengerGender">Gender</label>
                        <select id="passengerGender" name="passengerGender" class="form-control" required>
                            <option value="MALE">Male</option>
                            <option value="FEMALE">Female</option>
                            <option value="OTHER">Other</option>
                        </select>
                    </div>
                </div>

                <button type="submit" class="btn" style="width: 100%; margin-top: 1rem;">Confirm &amp; Generate PNR</button>
            </form>
        </div>
    </div>
</body>
</html>
