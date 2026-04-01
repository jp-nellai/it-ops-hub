FROM maven:3.9.5-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY pom.xml .
COPY backend/events-alarms-service .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/quarkus-app/lib/ ./lib/
COPY --from=builder /app/target/quarkus-app/*.jar ./
COPY --from=builder /app/target/quarkus-app/app/ ./app/
COPY --from=builder /app/target/quarkus-app/quarkus/ ./quarkus/

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "quarkus-run.jar"]
