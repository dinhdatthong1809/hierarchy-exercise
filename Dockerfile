FROM openjdk:17-alpine
MAINTAINER thong
COPY target/employee-*.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]