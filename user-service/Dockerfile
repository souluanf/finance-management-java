FROM maven:3.9.7-eclipse-temurin-21 AS build-env
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build-env /app/target/*.jar /app/app.jar
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=dev

CMD ["/app/wait-for-it.sh", "user_db", "--", "java", "-jar", "/app/app.jar"]