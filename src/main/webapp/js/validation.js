/**
 * Client-Side JavaScript Validation (Exp 07 & Exp 08)
 */

/**
 * Exp 08: Validate email address presence of '@' & '.' characters.
 * If missing, displays alert box reporting error and asks user to re-enter.
 */
function validateEmailPresenceCheck(email, inputElement) {
    if (email.indexOf('@') === -1 || email.indexOf('.') === -1) {
        alert("Validation Error: Email address must contain both '@' and '.' characters!\nPlease re-enter your email address again.");
        if (inputElement) {
            inputElement.focus();
            inputElement.select();
        }
        return false;
    }
    return true;
}

function validateLoginForm() {
    const emailField = document.forms["loginForm"]["email"];
    const email = emailField.value.trim();
    const password = document.forms["loginForm"]["password"].value.trim();

    if (email === "" || password === "") {
        alert("Please enter both email and password.");
        return false;
    }

    // Experiment 08 Check
    if (!validateEmailPresenceCheck(email, emailField)) {
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
