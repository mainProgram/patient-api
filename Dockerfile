FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongo:27017/patient-api-db", "-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
