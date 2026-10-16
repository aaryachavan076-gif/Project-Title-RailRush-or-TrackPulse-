<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>RailRush - Next-Gen Railway Booking &amp; TrackPulse</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="js/validation.js"></script>
</head>
<body>
    <header>
        <div class="nav-container">
            <a href="index.jsp" class="logo">🚆 Rail<span>Rush</span></a>
            <nav>
                <a href="index.jsp">Home</a>
                <a href="track_pulse.jsp">TrackPulse (Live Status)</a>
                <a href="ticket_confirmation.jsp">PNR Enquiry</a>
                <a href="login.jsp">Login</a>
                <a href="register.jsp" class="btn btn-sm">Register</a>
            </nav>
        </div>
    </header>

    <div class="container">
        <div class="card" style="text-align: center; background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%); color: white;">
            <h1 style="font-size: 2.5rem; margin-bottom: 0.5rem;">Indian Railway Ticketing &amp; Live Tracking</h1>
            <p style="font-size: 1.1rem; opacity: 0.9;">Fast, secure, real-time seat reservation powered by Enterprise Java &amp; Jakarta EE.</p>
        </div>

        <div class="card">
            <h2>Book Train Tickets</h2>
            <p style="color: var(--text-muted); margin-bottom: 1.5rem;">Search available trains across major Indian railway junctions</p>

            <form name="searchForm" action="SearchTrainServlet" method="get" onsubmit="return validateSearchForm()">
                <div style="display: grid; grid-template-columns: 1fr 1fr 1fr auto; gap: 1rem; align-items: flex-end;">
                    <div class="form-group" style="margin-bottom: 0;">
                        <label for="source">Source Station</label>
                        <select id="source" name="source" class="form-control" required>
                            <option value="">Select Origin</option>
                            <option value="CSMT Mumbai">CSMT Mumbai</option>
                            <option value="Dadar">Dadar</option>
                            <option value="Thane">Thane</option>
                            <option value="Pune Junction">Pune Junction</option>
                            <option value="Hazrat Nizamuddin">Hazrat Nizamuddin (Delhi)</option>
                            <option value="Madgaon (Goa)">Madgaon (Goa)</option>
                        </select>
                    </div>

                    <div class="form-group" style="margin-bottom: 0;">
                        <label for="destination">Destination Station</label>
                        <select id="destination" name="destination" class="form-control" required>
                            <option value="">Select Destination</option>
                            <option value="Madgaon (Goa)">Madgaon (Goa)</option>
                            <option value="Hazrat Nizamuddin">Hazrat Nizamuddin (Delhi)</option>
                            <option value="Pune Junction">Pune Junction</option>
                            <option value="CSMT Mumbai">CSMT Mumbai</option>
                        </select>
                    </div>

                    <div class="form-group" style="margin-bottom: 0;">
                        <label for="date">Journey Date</label>
                        <input type="date" id="date" name="date" class="form-control" required>
                    </div>

                    <div>
                        <button type="submit" class="btn" style="height: 48px;">Search Trains</button>
                    </div>
                </div>
            </form>
        </div>

        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 1.5rem;">
            <div class="card">
                <h3>🧭 TrackPulse Real-Time Telemetry</h3>
                <p style="margin: 0.5rem 0 1rem; color: var(--text-muted);">
                    Get real-time delays, intermediate arrival times, and platform numbers for live running trains.
                </p>
                <a href="track_pulse.jsp" class="btn btn-secondary">Open TrackPulse &rarr;</a>
            </div>

            <div class="card">
                <h3>🔍 Quick PNR Inquiry</h3>
                <form name="pnrForm" action="ticket_confirmation.jsp" method="get" onsubmit="return validatePNRForm()">
                    <div class="form-group">
                        <label for="pnr">Enter 10-Digit PNR Number</label>
                        <input type="text" id="pnr" name="pnr" class="form-control" placeholder="e.g. 8472910384" maxlength="10" required>
                    </div>
                    <button type="submit" class="btn">Check Status</button>
                </form>
            </div>
        </div>
    </div>

    <footer>
        <p>&copy; 2026 RailRush (TrackPulse). Full Stack Java Engineering Mini-Project.</p>
    </footer>
</body>
</html>
