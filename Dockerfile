# ---- Build stage ----
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests -B
# Extract the fat JAR in the builder (JDK available here) so BOOT-INF/lib/ can be copied later
RUN mkdir /tmp/app-extracted && cd /tmp/app-extracted && jar xf /app/target/playwright_process-0.0.1-SNAPSHOT.jar

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/playwright_process-0.0.1-SNAPSHOT.jar app.jar

# Copy all transitive dependency JARs extracted in the build stage
COPY --from=builder /tmp/app-extracted/BOOT-INF/lib /tmp/playwright-libs

# Download Chromium and install all required system dependencies via Playwright CLI
RUN java -cp "/tmp/playwright-libs/*" com.microsoft.playwright.CLI install --with-deps chromium

EXPOSE 8091
ENTRYPOINT ["java", "-jar", "app.jar"]
