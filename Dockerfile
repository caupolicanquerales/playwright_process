# ---- Build stage ----
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests -B

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/playwright_process-0.0.1-SNAPSHOT.jar app.jar

# Download Chromium and install all required system dependencies via Playwright CLI
RUN java -cp app.jar com.microsoft.playwright.CLI install --with-deps chromium

EXPOSE 8091
ENTRYPOINT ["java", "-jar", "app.jar"]
