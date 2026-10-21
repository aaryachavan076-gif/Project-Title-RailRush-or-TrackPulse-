<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register - RailRush</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <header>
        <div class="nav-container">
            <a href="index.jsp" class="logo">🚆 Rail<span>Rush</span></a>
            <nav>
                <a href="index.jsp">Home</a>
                <a href="experiments.jsp">Lab Suite (Exp 01-13)</a>
                <a href="spa.jsp">React SPA</a>
                <a href="login.jsp">Login</a>
                <a href="register.jsp">Register</a>
            </nav>
        </div>
    </header>

    <div class="container" style="max-width: 520px; margin-top: 3rem;">
        <div class="card">
            <h2 style="text-align: center; margin-bottom: 1.5rem;">Create Passenger Account</h2>

            <form action="RegisterServlet" method="post">
                <div class="form-group">
                    <label for="name">Full Name</label>
                    <input type="text" id="name" name="name" class="form-control" placeholder="Aarya Chavan" required>
                </div>

                <div class="form-group">
                    <label for="email">Email Address</label>
                    <input type="email" id="email" name="email" class="form-control" placeholder="name@example.com" required>
                </div>

                <div class="form-group">
                    <label for="phone">Phone Number</label>
                    <input type="tel" id="phone" name="phone" class="form-control" placeholder="9876543210" required>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" class="form-control" placeholder="At least 6 characters" minlength="6" required>
                </div>

                <button type="submit" class="btn" style="width: 100%; margin-top: 1rem;">Complete Registration</button>
            </form>

            <p style="text-align: center; margin-top: 1.5rem; color: var(--text-muted);">
                Already have an account? <a href="login.jsp" style="color: var(--primary); font-weight: 600;">Sign In</a>
            </p>
        </div>
    </div>
</body>
</html>
