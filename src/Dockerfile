FROM gradle:7.3.3-jdk11 AS build
COPY . .
RUN gradle clean build

FROM openjdk:11-jre-slim
COPY --from=build build/libs/Digital_Shop-0.0.1-SNAPSHOT-plain.jar Digital_Shop.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "Digital_Shop.jar"]