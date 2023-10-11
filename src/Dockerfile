
FROM gradle:6.9.0-jdk11 AS build
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
COPY src ./src
RUN ./gradlew clean build

# Production stage
FROM openjdk:17.0.1-jre-slim
COPY --from=build /home/gradle/build/libs/Digital_Shop-0.0.1-SNAPSHOT-plain.jar Digital_Shop.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "Digital_Shop.jar"]