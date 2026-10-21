/**
 * Experiment 09: DOM-Based Automatic Background Color Changer (every 5 seconds).
 * Syllabus Requirement: Program based on Document Object Model to change the background
 * color of the web page automatically after every 5 seconds.
 */

var colorPalette = [
    { name: "Midnight Navy", hex: "#0f172a", text: "#f8fafc" },
    { name: "Deep Indigo", hex: "#1e1b4b", text: "#f8fafc" },
    { name: "Rail Signal Teal", hex: "#0f2e2e", text: "#f8fafc" },
    { name: "Track Dark Blue", hex: "#172554", text: "#f8fafc" },
    { name: "Slate Express", hex: "#1e293b", text: "#f8fafc" },
    { name: "Emerald Depot", hex: "#062e1c", text: "#f8fafc" },
    { name: "Amber Station Glow", hex: "#2b1e06", text: "#fde68a" },
    { name: "Royal Railway Violet", hex: "#2e1065", text: "#f5d0fe" }
];

var currentColorIndex = 0;
var colorIntervalId = null;
var timerCountdown = 5;
var countdownIntervalId = null;
var isAutoColorActive = false;

function changeBackgroundColor(targetId) {
    currentColorIndex = (currentColorIndex + 1) % colorPalette.length;
    var selected = colorPalette[currentColorIndex];

    var targetElement = targetId ? document.getElementById(targetId) : document.body;
    if (targetElement) {
        targetElement.style.transition = "background-color 0.8s ease-in-out, color 0.5s ease";
        targetElement.style.backgroundColor = selected.hex;
    }

    var indicator = document.getElementById("activeColorName");
    if (indicator) {
        indicator.textContent = selected.name + " (" + selected.hex + ")";
        indicator.style.color = selected.text;
    }

    var badge = document.getElementById("colorSwatch");
    if (badge) {
        badge.style.backgroundColor = selected.hex;
    }

    timerCountdown = 5;
}

function startDomColorChanger(targetId) {
    if (isAutoColorActive) return;
    isAutoColorActive = true;
    timerCountdown = 5;

    // Trigger every 5 seconds (5000 milliseconds)
    colorIntervalId = setInterval(function() {
        changeBackgroundColor(targetId);
    }, 5000);

    countdownIntervalId = setInterval(function() {
        timerCountdown--;
        if (timerCountdown <= 0) timerCountdown = 5;
        var countdownEl = document.getElementById("countdownTimer");
        if (countdownEl) {
            countdownEl.textContent = timerCountdown + "s";
        }
    }, 1000);

    var statusEl = document.getElementById("domColorStatus");
    if (statusEl) {
        statusEl.textContent = "ACTIVE (Switching every 5 seconds)";
        statusEl.className = "badge badge-success";
    }
}

function stopDomColorChanger() {
    if (colorIntervalId) {
        clearInterval(colorIntervalId);
        colorIntervalId = null;
    }
    if (countdownIntervalId) {
        clearInterval(countdownIntervalId);
        countdownIntervalId = null;
    }
    isAutoColorActive = false;

    var statusEl = document.getElementById("domColorStatus");
    if (statusEl) {
        statusEl.textContent = "PAUSED";
        statusEl.className = "badge badge-warning";
    }
}

function toggleDomColorChanger(targetId) {
    if (isAutoColorActive) {
        stopDomColorChanger();
    } else {
        startDomColorChanger(targetId);
    }
}
