FROM openjdk:17
MAINTAINER thong
COPY target/employee-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]