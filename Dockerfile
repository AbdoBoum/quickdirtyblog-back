# Build stage

FROM maven AS BUILD_STAGE
WORKDIR app/
COPY . .
RUN ["mvn", "clean", "install"]

# Run stage

FROM openjdk:11.0.6-jre-slim
WORKDIR app/
COPY --from=BUILD_STAGE /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]