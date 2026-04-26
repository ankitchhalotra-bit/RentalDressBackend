# ==================================================
# Dockerfile for Spring Boot Project (JDK 21)
# Exact JAR Name: demo-0.0.1-SNAPSHOT.jar
# Docker Hub + EC2 + CI/CD Ready
# ==================================================

# ---------- Build Stage ----------
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy project files
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Give permission
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build project
RUN ./mvnw clean package -DskipTests

# ---------- Runtime Stage ----------
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy exact jar file
COPY --from=builder /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose application port
EXPOSE 8080

# Start application
ENTRYPOINT ["java","-jar","app.jar"]
