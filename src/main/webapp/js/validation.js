/**
 * Client-Side JavaScript Validation (Exp 07)
 */

function validateLoginForm() {
    const email = document.forms["loginForm"]["email"].value.trim();
    const password = document.forms["loginForm"]["password"].value.trim();

    if (email === "" || password === "") {
        alert("Please enter both email and password.");
        return false;
    }

    // Email format validation (contains '@' and '.')
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        alert("Please enter a valid email address containing '@' and '.'");
        return false;
    }

    if (password.length < 6) {
        alert("Password must be at least 6 characters long.");
        return false;
    }

    return true;
}

function validateSearchForm() {
    const source = document.forms["searchForm"]["source"].value.trim();
    const destination = document.forms["searchForm"]["destination"].value.trim();
    const date = document.forms["searchForm"]["date"].value;

    if (source === "" || destination === "") {
        alert("Please select both source and destination stations.");
        return false;
    }

    if (source.toLowerCase() === destination.toLowerCase()) {
        alert("Source and Destination stations cannot be identical!");
        return false;
    }

    if (date === "") {
        alert("Please choose a travel date.");
        return false;
    }

    return true;
}

function validateBookingForm() {
    const name = document.forms["bookingForm"]["passengerName"].value.trim();
    const age = parseInt(document.forms["bookingForm"]["passengerAge"].value, 10);

    if (name.length < 3) {
        alert("Passenger name must be at least 3 characters.");
        return false;
    }

    if (isNaN(age) || age < 1 || age > 120) {
        alert("Please enter a valid age between 1 and 120.");
        return false;
    }

    return true;
}

function validatePNRForm() {
    const pnr = document.forms["pnrForm"]["pnr"].value.trim();
    if (!/^\d{10}$/.test(pnr)) {
        alert("PNR Number must be exactly 10 numeric digits.");
        return false;
    }
    return true;
}
