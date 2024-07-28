
# Launching the QA Application Locally

## Prerequisites

- Docker
## Introduction

The setup includes a MariaDB database and the application running with the `local` profile. This ensures that Liquibase can seed the database with the necessary tables.
## Steps to Launch the Application

1. **Navigate to the Project Root Directory**

   Open your terminal and navigate to the root directory of the project:
   ```sh
   cd ~/qa
   ```

2. **Run Docker Compose**

   Execute the following command to start the application and MariaDB services:
   ```sh
   docker-compose up
   ```

   > **Note:** The first time you run this command, it may fail with a "connection refused" error. This is expected due to the time it takes for MariaDB to initialize. Run the command again to successfully start the application.

3. **Accessing Swagger UI**

   Once the application is up and running, you can access the Swagger UI at:
   ```
   http://localhost:8090/swagger-ui/index.html
   ```

## Generating Code Coverage Report

To generate the code coverage report, run the following Gradle command:
```sh
./gradlew codeCoverageReport --build-cache --no-daemon
```

The report will be generated and can be viewed at:
```
~/qa/build/reports/jacoco/codeCoverageReport/html/index.html
```

## BDD Tests

The BDD tests are located in the `application` module of the project.

## Conclusion

By following these steps, you should be able to launch the application locally, access the Swagger UI for API documentation, and generate code coverage reports.