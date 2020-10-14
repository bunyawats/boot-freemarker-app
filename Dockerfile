# FROM openjdk:8-jdk-alpine
FROM maven:3.5-jdk-8
VOLUME /tmp
ADD /target/freemarker-app-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]