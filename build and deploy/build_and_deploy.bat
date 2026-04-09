@echo off
REM ==========================================
REM Build Maven project and run Docker Compose
REM ==========================================

cd ..

REM Step 1: Run Maven package
echo Running Maven build...
mvn clean package -DskipTests && echo Build successful! && echo Starting Docker Compose... && docker-compose up --build
IF %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Docker Compose failed.
    exit /b %ERRORLEVEL%
)

REM Optional: Pause so window stays open
pause
