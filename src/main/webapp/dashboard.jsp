<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%
    // Exp 06: Implicit Object 'session' authentication verification
    String userName = (String) session.getAttribute("userName");
    String userRole = (String) session.getAttribute("userRole");

    if (userName == null) {
        response.sendRedirect("login.jsp?error=Please log in to access your dashboard");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Passenger Dashboard - RailRush</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <header>
        <div class="nav-container">
            <a href="index.jsp" class="logo">🚆 Rail<span>Rush</span></a>
            <nav>
                <a href="dashboard.jsp">Dashboard</a>
                <a href="track_pulse.jsp">TrackPulse</a>
                <a href="LogoutServlet" class="btn btn-sm btn-secondary" style="margin-left: 2rem;">Logout</a>
            </nav>
        </div>
    </header>

    <div class="container">
        <div class="card" style="background: linear-gradient(135deg, #1e3c72, #2a5298); color: white;">
            <%-- Exp 06: Implicit Object 'out' --%>
            <h2>Welcome back, <% out.print(userName); %>! 👋</h2>
            <p style="opacity: 0.9; margin-top: 0.25rem;">
                Role: <strong><%= userRole %></strong> |
                Session Active Since: <%= new Date(session.getCreationTime()) %>
            </p>
        </div>

        <!-- Quick Actions -->
        <div style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 1.5rem; margin-bottom: 2rem;">
            <div class="card" style="text-align: center;">
                <div style="font-size: 2.5rem;">🎫</div>
                <h3 style="margin: 0.5rem 0;">Book New Ticket</h3>
                <p style="color: var(--text-muted); font-size: 0.9rem; margin-bottom: 1rem;">Reserve seats with instant PNR allocation</p>
                <a href="index.jsp" class="btn">Search Trains</a>
            </div>

            <div class="card" style="text-align: center;">
                <div style="font-size: 2.5rem;">🧭</div>
                <h3 style="margin: 0.5rem 0;">TrackPulse Live</h3>
                <p style="color: var(--text-muted); font-size: 0.9rem; margin-bottom: 1rem;">Check live delays and station arrivals</p>
                <a href="track_pulse.jsp" class="btn btn-secondary">Track Fleet</a>
            </div>

            <div class="card" style="text-align: center;">
                <div style="font-size: 2.5rem;">📋</div>
                <h3 style="margin: 0.5rem 0;">PNR Status</h3>
                <p style="color: var(--text-muted); font-size: 0.9rem; margin-bottom: 1rem;">Verify booking status and seat details</p>
                <a href="ticket_confirmation.jsp" class="btn btn-secondary">Enquire PNR</a>
            </div>
        </div>

        <!-- Exp 06: Explicit JavaBean usage via standard JSP action tags -->
        <jsp:useBean id="featuredTrain" class="com.railrush.model.Train" scope="page" />
        <jsp:setProperty name="featuredTrain" property="trainNumber" value="12051" />
        <jsp:setProperty name="featuredTrain" property="trainName" value="Jan Shatabdi Express" />
        <jsp:setProperty name="featuredTrain" property="sourceStation" value="CSMT Mumbai" />
        <jsp:setProperty name="featuredTrain" property="destinationStation" value="Madgaon (Goa)" />
        <jsp:setProperty name="featuredTrain" property="fareSleeper" value="320.0" />

        <div class="card">
            <h3>🌟 Featured Express Train (JSP JavaBean Tag Demonstration)</h3>
            <p style="color: var(--text-muted); font-size: 0.9rem; margin-bottom: 1rem;">
                Rendered using <code>&lt;jsp:useBean&gt;</code> and <code>&lt;jsp:getProperty&gt;</code> tags.
            </p>
            <table class="table">
                <tr>
                    <th>Train Number</th>
                    <th>Train Name</th>
                    <th>Route</th>
                    <th>Base Fare</th>
                </tr>
                <tr>
                    <td><strong><jsp:getProperty name="featuredTrain" property="trainNumber" /></strong></td>
                    <td><jsp:getProperty name="featuredTrain" property="trainName" /></td>
                    <td><jsp:getProperty name="featuredTrain" property="sourceStation" /> &rarr; <jsp:getProperty name="featuredTrain" property="destinationStation" /></td>
                    <td>₹<jsp:getProperty name="featuredTrain" property="fareSleeper" />0</td>
                </tr>
            </table>
        </div>
    </div>
</body>
</html>
