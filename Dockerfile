FROM openjdk:17-alpine
MAINTAINER thong
COPY target/employee-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]