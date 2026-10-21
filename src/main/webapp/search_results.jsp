<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.railrush.model.Train" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Available Trains - RailRush</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <header>
        <div class="nav-container">
            <a href="index.jsp" class="logo">🚆 Rail<span>Rush</span></a>
            <nav>
                <a href="index.jsp">New Search</a>
                <a href="experiments.jsp">Lab Suite (Exp 01-13)</a>
                <a href="spa.jsp">React SPA</a>
                <a href="track_pulse.jsp">TrackPulse</a>
                <a href="dashboard.jsp">Dashboard</a>
            </nav>
        </div>
    </header>

    <div class="container">
        <h2>Available Trains</h2>
        <p style="color: var(--text-muted); margin-bottom: 1.5rem;">
            Showing schedule and seat availability for your selected route
        </p>

        <%
            List<Train> trains = (List<Train>) request.getAttribute("trains");
            String journeyDate = (String) request.getAttribute("journeyDate");
            if (journeyDate == null || journeyDate.isEmpty()) {
                journeyDate = "2026-10-25";
            }

            if (trains == null || trains.isEmpty()) {
        %>
            <div class="card" style="text-align: center; padding: 3rem;">
                <h3>No Trains Found on Selected Route</h3>
                <p style="color: var(--text-muted); margin: 0.5rem 0 1.5rem;">Try modifying your origin or destination station.</p>
                <a href="index.jsp" class="btn">Modify Search</a>
            </div>
        <%
            } else {
        %>
            <div class="card">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Train No &amp; Name</th>
                            <th>Departure &rarr; Arrival</th>
                            <th>Available Seats</th>
                            <th>Fares</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Train t : trains) { %>
                            <tr>
                                <td>
                                    <strong><%= t.getTrainNumber() %></strong><br>
                                    <span style="color: var(--primary);"><%= t.getTrainName() %></span>
                                </td>
                                <td>
                                    <strong><%= t.getSourceStation() %></strong> (<%= t.getDepartureTime() %>)<br>
                                    &darr;<br>
                                    <strong><%= t.getDestinationStation() %></strong> (<%= t.getArrivalTime() %>)
                                </td>
                                <td>
                                    <% if (t.getAvailableSeats() > 50) { %>
                                        <span class="badge badge-success"><%= t.getAvailableSeats() %> Seats Available</span>
                                    <% } else if (t.getAvailableSeats() > 0) { %>
                                        <span class="badge badge-warning"><%= t.getAvailableSeats() %> Seats Left</span>
                                    <% } else { %>
                                        <span class="badge badge-danger">Sold Out (WL)</span>
                                    <% } %>
                                </td>
                                <td>
                                    Sleeper: <strong>₹<%= t.getFareSleeper() %></strong><br>
                                    AC Tier: <strong>₹<%= t.getFareAc() %></strong>
                                </td>
                                <td>
                                    <% if (t.getAvailableSeats() > 0) { %>
                                        <a href="booking.jsp?trainNumber=<%= t.getTrainNumber() %>&journeyDate=<%= journeyDate %>" class="btn" style="padding: 0.4rem 0.8rem; font-size: 0.9rem;">Book Now</a>
                                    <% } else { %>
                                        <button class="btn" style="padding: 0.4rem 0.8rem; font-size: 0.9rem; background: #94a3b8; cursor: not-allowed;" disabled>Regret</button>
                                    <% } %>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        <%
            }
        %>
    </div>
</body>
</html>
