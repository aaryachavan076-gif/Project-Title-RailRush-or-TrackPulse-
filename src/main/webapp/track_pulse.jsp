<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="com.railrush.dao.DBConnection" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>TrackPulse - Real-Time Train Tracking &amp; Delays</title>
    <link rel="stylesheet" href="css/style.css">
    <meta http-equiv="refresh" content="30">
</head>
<body>
    <header>
        <div class="nav-container">
            <a href="index.jsp" class="logo">🚆 Track<span>Pulse</span> <span style="font-size: 0.8rem; font-weight: 400; opacity: 0.8;">by RailRush</span></a>
            <nav>
                <a href="index.jsp">Home</a>
                <a href="dashboard.jsp">Dashboard</a>
                <a href="ticket_confirmation.jsp">PNR Status</a>
            </nav>
        </div>
    </header>

    <div class="container">
        <div class="card" style="display: flex; justify-content: space-between; align-items: center; background: #0f172a; color: white;">
            <div>
                <h2>Live Train Movement Telemetry</h2>
                <p style="opacity: 0.8; font-size: 0.9rem;">Auto-refreshing every 30 seconds with simulated GPS telemetry</p>
            </div>
            <div>
                <span class="badge badge-success" style="padding: 0.5rem 1rem;">● LIVE FEED ACTIVE</span>
            </div>
        </div>

        <div class="card">
            <h3>Active Fleet Status</h3>
            <table class="table">
                <thead>
                    <tr>
                        <th>Train</th>
                        <th>Current Location</th>
                        <th>Next En-Route Station</th>
                        <th>Delay Status</th>
                        <th>Last Telemetry Sync</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        String sql = "SELECT lt.*, t.train_name FROM live_tracking lt " +
                                     "JOIN trains t ON lt.train_number = t.train_number";
                        try (Connection conn = DBConnection.getConnection();
                             PreparedStatement ps = conn.prepareStatement(sql);
                             ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                int delay = rs.getInt("delay_minutes");
                    %>
                        <tr>
                            <td>
                                <strong><%= rs.getString("train_number") %></strong><br>
                                <span style="color: var(--primary);"><%= rs.getString("train_name") %></span>
                            </td>
                            <td>📍 <strong><%= rs.getString("current_station") %></strong></td>
                            <td>&rarr; <%= rs.getString("next_station") %></td>
                            <td>
                                <% if (delay == 0) { %>
                                    <span class="badge badge-success">On Time</span>
                                <% } else { %>
                                    <span class="badge badge-warning">Delayed by <%= delay %> mins</span>
                                <% } %>
                            </td>
                            <td><%= rs.getTimestamp("status_updated_at") %></td>
                        </tr>
                    <%
                            }
                        } catch (Exception e) {
                    %>
                        <tr>
                            <td colspan="5" style="color: red;">Database connecting or running offline mock data.</td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
