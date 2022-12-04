FROM eclipse-temurin:17-jre-alpine
MAINTAINER thong
COPY target/employee-*.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]