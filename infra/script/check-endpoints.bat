@echo off

REM Vérification du point de terminaison API
for /f %%i in ('curl -s -o NUL -w "%%{http_code}" http://localhost:9010/persons') do set API_STATUS=%%i
echo API Response Code: %API_STATUS%
if not "%API_STATUS%"=="200" (
    echo Error: API endpoint check failed. Status: %API_STATUS%
    exit /b 1
)

REM Vérification du point de terminaison WEB
for /f %%i in ('curl -s -o NUL -w "%%{http_code}" http://localhost:9090/') do set WEB_STATUS=%%i
echo WEB Response Code: %WEB_STATUS%
if not "%WEB_STATUS%"=="200" (
    echo Error: WEB endpoint check failed. Status: %WEB_STATUS%
    exit /b 1
)

echo Both endpoints are up and running.
exit /b 0