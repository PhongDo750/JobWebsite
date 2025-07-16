# Stage 1: build
FROM maven:3.9.8-amazoncorretto-21 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests

# Stage 2: run
FROM amazoncorretto:21.0.4

WORKDIR /app

# Đổi dòng này để copy đúng file cụ thể
COPY --from=build /app/target/JobWebsite-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
