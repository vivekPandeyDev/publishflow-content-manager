:: Navigate to the publishflow directory and run Maven clean install
echo Navigating to ./publishflow/ directory...
cd ./publishflow/

echo Running Maven clean install...
./mvnw clean install

:: Check if Maven build was successful
if %errorlevel% neq 0 (
    echo Maven build failed. Exiting.
    exit /b %errorlevel%
)
