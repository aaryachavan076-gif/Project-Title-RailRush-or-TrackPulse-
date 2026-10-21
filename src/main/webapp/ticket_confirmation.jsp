<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.railrush.dao.BookingDAO" %>
<%@ page import="com.railrush.model.Ticket" %>
<%@ page import="com.railrush.model.Passenger" %>
<%
    String pnr = request.getParameter("pnr");
    Ticket ticket = null;
    if (pnr != null && !pnr.trim().isEmpty()) {
        try {
            BookingDAO dao = new BookingDAO();
            ticket = dao.getTicketByPNR(pnr.trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PNR Enquiry &amp; E-Ticket - RailRush</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="js/validation.js"></script>
    <style>
        @media print {
            header, .no-print { display: none; }
            .card { box-shadow: none; border: 1px solid #000; }
        }
    </style>
</head>
<body>
    <header>
        <div class="nav-container">
            <a href="index.jsp" class="logo">🚆 Rail<span>Rush</span></a>
            <nav>
                <a href="index.jsp">Home</a>
                <a href="experiments.jsp">Lab Suite (Exp 01-13)</a>
                <a href="spa.jsp">React SPA</a>
                <a href="dashboard.jsp">Dashboard</a>
                <a href="track_pulse.jsp">TrackPulse</a>
            </nav>
        </div>
    </header>

    <div class="container" style="max-width: 800px;">
        <div class="card no-print">
            <h3>🔍 Search PNR Status</h3>
            <form name="pnrForm" action="ticket_confirmation.jsp" method="get" onsubmit="return validatePNRForm()" style="display: flex; gap: 1rem; margin-top: 1rem;">
                <input type="text" name="pnr" class="form-control" placeholder="Enter 10-digit PNR" value="<%= pnr != null ? pnr : "" %>" required maxlength="10">
                <button type="submit" class="btn" style="white-space: nowrap;">Check Status</button>
            </form>
        </div>

        <% if (ticket != null) { %>
            <div class="card" style="border-top: 4px solid var(--accent);">
                <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 2px dashed var(--border); padding-bottom: 1rem;">
                    <div>
                        <span style="font-size: 0.85rem; color: var(--text-muted); text-transform: uppercase; letter-spacing: 1px;">Passenger Name Record</span>
                        <h2 style="color: var(--primary); font-size: 2rem; margin: 0;">PNR: <%= ticket.getPnrNumber() %></h2>
                    </div>
                    <div>
                        <span class="badge badge-success" style="font-size: 1rem; padding: 0.5rem 1rem;"><%= ticket.getStatus() %></span>
                    </div>
                </div>

                <div style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 1.5rem; margin: 1.5rem 0;">
                    <div>
                        <span style="color: var(--text-muted); font-size: 0.85rem;">Train</span>
                        <div style="font-weight: 700;"><%= ticket.getTrainNumber() %> - <%= ticket.getTrainName() %></div>
                    </div>
                    <div>
                        <span style="color: var(--text-muted); font-size: 0.85rem;">Route</span>
                        <div style="font-weight: 700;"><%= ticket.getSourceStation() %> &rarr; <%= ticket.getDestinationStation() %></div>
                    </div>
                    <div>
                        <span style="color: var(--text-muted); font-size: 0.85rem;">Date of Journey</span>
                        <div style="font-weight: 700;"><%= ticket.getJourneyDate() %></div>
                    </div>
                </div>

                <h4 style="margin-top: 1.5rem;">Passenger Details</h4>
                <table class="table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Passenger Name</th>
                            <th>Age</th>
                            <th>Gender</th>
                            <th>Allocated Seat</th>
                            <th>Class</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            int idx = 1;
                            for (Passenger p : ticket.getPassengers()) {
                        %>
                            <tr>
                                <td><%= idx++ %></td>
                                <td><strong><%= p.getName() %></strong></td>
                                <td><%= p.getAge() %></td>
                                <td><%= p.getGender() %></td>
                                <td><span class="badge badge-success"><%= p.getSeatNumber() %></span></td>
                                <td><%= ticket.getTravelClass() %></td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>

                <div style="margin-top: 1.5rem; padding-top: 1rem; border-top: 1px solid var(--border); display: flex; justify-content: space-between; align-items: center;">
                    <div>Total Fare Paid: <strong style="font-size: 1.3rem; color: var(--accent);">₹<%= ticket.getTotalFare() %>0</strong></div>
                    <button onclick="window.print()" class="btn btn-secondary no-print">🖨️ Print E-Ticket</button>
                </div>
            </div>
        <% } else if (pnr != null && !pnr.isEmpty()) { %>
            <div class="card" style="text-align: center; padding: 3rem;">
                <h3>No Booking Found for PNR <%= pnr %></h3>
                <p style="color: var(--text-muted);">Please check the 10-digit number and try again.</p>
            </div>
        <% } %>
    </div>
</body>
</html>
