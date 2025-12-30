@echo off
REM Code Coach Frontend - Launch Script (Windows)
REM Usage: launch.bat

echo.
echo 🎓 Code Coach Frontend MVP - Launcher
echo ======================================
echo.

REM Check Node.js
where node >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Node.js not found. Please install Node.js 18+
    exit /b 1
)

for /f "tokens=*" %%i in ('node -v') do set NODE_VERSION=%%i
echo ✅ Node.js %NODE_VERSION% detected

REM Check npm
where npm >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ❌ npm not found
    exit /b 1
)

for /f "tokens=*" %%i in ('npm -v') do set NPM_VERSION=%%i
echo ✅ npm %NPM_VERSION% detected
echo.

REM Install dependencies
if not exist "node_modules" (
    echo 📦 Installing dependencies...
    call npm install
    echo ✅ Dependencies installed
) else (
    echo ✅ Dependencies already installed
)

echo.
echo 🚀 Starting development server...
echo ======================================
echo.
echo 📍 Frontend will open at: http://localhost:3000
echo 📍 Make sure backend is running at: http://localhost:8080
echo.
echo Press Ctrl+C to stop the server
echo.

REM Start dev server
call npm run dev

pause
