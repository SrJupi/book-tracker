# ---- Build stage ----
FROM eclipse-temurin:21-jdk AS build

# Set work directory
WORKDIR /app

# Copy Maven wrapper and pom.xml first
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (cached if pom.xml unchanged)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src
COPY contracts contracts

# Build the application
RUN ./mvnw package -DskipTests -B

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre-alpine AS runtime

WORKDIR /app

# Copy built jar
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
