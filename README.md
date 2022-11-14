### How to run in local
**Make sure port `8080` and `5432` are free.**

In `project folder`, run
1. Turn Spring boot application into Docker image
   - `mvn clean package`
   - `docker build -t thong-exercise-employee-hierarchy .`
2. Run Docker compose 
   - `docker-compose up -d`

- Spring Boot application runs on port `8080`
- PostgreSQL database runs on port `5432`

### How to test APIs in local
Using Postman, please go to `project folder` and import collection file `employee-hierarchy-excercise.postman_collection.json` into Postman

### To start the application in IDE (IntelliJ IDEA is recommended)
Make sure the application or integration tests run with environment variables (please adapt the value based on your environment):
```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5455/test;
SPRING_DATASOURCE_PASSWORD=postgres;
SPRING_DATASOURCE_USERNAME=postgres
```

Thank you