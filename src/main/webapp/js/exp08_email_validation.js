/**
 * Experiment 08: Program using JavaScript to validate email address.
 * Syllabus Specification: Check the presence of '@' & '.' characters.
 * If missing, display an alert box reporting the error and ask the user to re-enter it again.
 */

function validateEmailPresence(inputElementId) {
    var emailInput = document.getElementById(inputElementId);
    if (!emailInput) {
        return false;
    }
    
    var email = emailInput.value.trim();

    if (email === "") {
        alert("Email field cannot be empty. Please enter your email address.");
        emailInput.focus();
        return false;
    }

    var hasAtSymbol = email.indexOf("@") !== -1;
    var hasDotSymbol = email.indexOf(".") !== -1;

    // Requirement: Check presence of '@' and '.' characters
    if (!hasAtSymbol || !hasDotSymbol) {
        var missingChars = [];
        if (!hasAtSymbol) missingChars.push("'@'");
        if (!hasDotSymbol) missingChars.push("'.'");

        alert("Validation Error: Email address must contain the character(s) " + missingChars.join(" and ") + ".\nPlease re-enter your email address again.");
        emailInput.focus();
        emailInput.select();
        return false;
    }

    // Additional positioning checks for robust UX
    var atIndex = email.indexOf("@");
    var lastDotIndex = email.lastIndexOf(".");

    if (atIndex === 0 || lastDotIndex < atIndex + 2 || lastDotIndex === email.length - 1) {
        alert("Validation Error: Invalid email format. The '@' must appear before '.' and have domain characters.\nPlease re-enter your email address again.");
        emailInput.focus();
        emailInput.select();
        return false;
    }

    alert("Success: Email validation passed! Both '@' and '.' are present.");
    return true;
}
