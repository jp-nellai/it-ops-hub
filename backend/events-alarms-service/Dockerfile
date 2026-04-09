FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY target/quarkus-app/ /app/

# Stage 2: Runtime image
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/ /app/
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/quarkus-run.jar"]