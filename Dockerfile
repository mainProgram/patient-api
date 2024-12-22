FROM maven:3.8.5-openjdk-17-slim

RUN mkdir /api-app
WORKDIR /api-app

COPY pom.xml ./
RUN mvn dependency:go-offline

COPY docker-entrypoint.sh ./
COPY src ./src

#FROM maven:3.8.7-openjdk-18-slim
#
#RUN mkdir /api-app
#WORKDIR /api-app
#
#COPY pom.xml ./
#RUN mvn dependency:go-offline
#
#COPY docker-entrypoint.sh ./
#COPY src ./src
#
#FROM maven:3.8.5-openjdk-17 AS build
#WORKDIR /app
#COPY pom.xml .
#COPY src ./src
#RUN mvn clean install -DskipTests
#
#FROM openjdk:17-jdk-slim
#WORKDIR /app
#COPY --from=build /app/target/*.jar app.jar
#EXPOSE 8084
#ENTRYPOINT ["java", "-jar", "app.jar"]
