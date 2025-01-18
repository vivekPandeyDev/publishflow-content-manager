@echo off

:: Run the Spring Boot application with environment variables
setlocal

:: Set environment variables for the Java process
set KEYCLOAK_ADMIN_CLIENT_ID=admin-cli
set KEYCLOAK_CLIENT_SECRET=KHCxBgOp7e3K3tAdM79MpgFyyKWjbmRe
set KEYCLOAK_DOMAIN=http://localhost:9090
set KEYCLOAK_REALM=auth-server
set OPEN_ID_HOST=localhost
set SPRING_DATASOURCE_PASSWORD=password
set SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/content-manager
set SPRING_DATASOURCE_USERNAME=postgres
set JWT_ISSUER_URI=http://localhost:9090/realms/auth-server
set JWT_RESOURCE_ID=admin-cli
set OPEN_ID_HOST=localhost
set JWT_RESOURCE_SECRET=KHCxBgOp7e3K3tAdM79MpgFyyKWjbmRe
:: Run the Spring Boot application with logging configuration
java -Dlogging.file.name=application.log -jar  -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 ./publishflow/target/publishflow-0.0.1-SNAPSHOT.jar
:: End the environment variable scope
endlocal

:: Pause to keep the window open if needed
pause
