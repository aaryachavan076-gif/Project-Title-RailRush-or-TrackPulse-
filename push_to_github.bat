@echo off
title RailRush - Push to GitHub
echo ====================================================================
echo       RailRush (TrackPulse) - Push to GitHub Repository
echo       Target: https://github.com/aaryachavan076-gif/Project-Title-RailRush-or-TrackPulse-
echo ====================================================================
echo.

git push -u origin main
if %ERRORLEVEL% EQU 0 (
    echo.
    echo ====================================================================
    echo [SUCCESS] RailRush has been successfully pushed to GitHub!
    echo View repository at: https://github.com/aaryachavan076-gif/Project-Title-RailRush-or-TrackPulse-
    echo ====================================================================
) else (
    echo.
    echo ====================================================================
    echo [NOTE] Git needs your GitHub authentication.
    echo If prompted above, enter your GitHub Username and Personal Access Token (PAT).
    echo.
    echo Alternatively, enter your GitHub Personal Access Token below:
    echo (Create one at https://github.com/settings/tokens with 'repo' scope)
    echo ====================================================================
    set /p TOKEN="GitHub Personal Access Token: "
    if defined TOKEN (
        git push https://%TOKEN%@github.com/aaryachavan076-gif/Project-Title-RailRush-or-TrackPulse-.git main
        if %ERRORLEVEL% EQU 0 (
            echo.
            echo [SUCCESS] Push completed successfully!
        )
    )
)

echo.
pause
