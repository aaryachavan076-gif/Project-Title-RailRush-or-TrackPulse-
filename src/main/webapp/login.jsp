<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - RailRush</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="js/validation.js"></script>
</head>
<body>
    <header>
        <div class="nav-container">
            <a href="index.jsp" class="logo">🚆 Rail<span>Rush</span></a>
            <nav>
                <a href="index.jsp">Home</a>
                <a href="track_pulse.jsp">TrackPulse</a>
                <a href="login.jsp">Login</a>
                <a href="register.jsp">Register</a>
            </nav>
        </div>
    </header>

    <div class="container" style="max-width: 480px; margin-top: 4rem;">
        <div class="card">
            <h2 style="text-align: center; margin-bottom: 1.5rem;">Passenger Login</h2>

            <%-- Display status parameter forwarded from LoginServlet or RegisterServlet --%>
            <%
                String registered = request.getParameter("registered");
                if ("true".equals(registered)) {
            %>
                <div class="alert alert-success">Registration successful! Please sign in with your credentials.</div>
            <%
                }
                String error = request.getParameter("error");
                if (error != null) {
            %>
                <div class="alert alert-danger"><%= error %></div>
            <%
                }
            %>

            <form name="loginForm" action="LoginServlet" method="post" onsubmit="return validateLoginForm()">
                <div class="form-group">
                    <label for="email">Registered Email Address</label>
                    <input type="email" id="email" name="email" class="form-control" placeholder="user@example.com" required>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" class="form-control" placeholder="••••••••" required>
                </div>

                <button type="submit" class="btn" style="width: 100%; margin-top: 1rem;">Sign In</button>
            </form>

            <p style="text-align: center; margin-top: 1.5rem; color: var(--text-muted);">
                Don't have an account? <a href="register.jsp" style="color: var(--primary); font-weight: 600;">Register Here</a>
            </p>

            <div style="margin-top: 1.5rem; padding-top: 1rem; border-top: 1px solid var(--border); font-size: 0.85rem; color: var(--text-muted);">
                <strong>Demo Credentials:</strong><br>
                Admin: <code>admin@railrush.in</code> / <code>admin@123</code><br>
                Passenger: <code>aarya@example.com</code> / <code>user@123</code>
            </div>
        </div>
    </div>

    <footer>
        <p>&copy; 2026 RailRush (TrackPulse). Full Stack Java Mini-Project.</p>
    </footer>
</body>
</html>
