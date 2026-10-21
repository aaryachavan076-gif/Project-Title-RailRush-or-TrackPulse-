@echo off
title RailRush - Local Live Web Server
echo ====================================================================
echo                 🚆 RailRush (TrackPulse) Live Server
echo ====================================================================
echo.
echo Starting local web server on port 5000...
echo URL: http://localhost:5000/index.html
echo.
echo Launching your default web browser...
start "" "http://localhost:5000/index.html"
echo.
echo Web server is running. Press Ctrl+C in this window to stop.
echo ====================================================================
python -m http.server 5000
pause
