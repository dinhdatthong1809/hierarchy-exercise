### How to run in local
**Make sure port `8080` and `5432` is free.**

In folder `employee`, run
1. Turn Spring boot application into Docker image
   - `mvn clean package`
   - `docker build -t thong-exercise-employee-hierarchy .`
2. Run Docker compose 
   - `docker-compose up -d`

- Spring Boot application runs on port `8080`
- PostgreSQL database runs on port `5432`