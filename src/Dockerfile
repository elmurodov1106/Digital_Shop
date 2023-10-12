
FROM gradle:jdk17-jammy AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

LABEL org.name="hezf"
FROM eclipse-temurin:17-jdk-jammy
COPY --from=build /home/gradle/build/libs/Digital_Shop-0.0.1-SNAPSHOT-plain.jar Digital_Shop.jar
ENTRYPOINT ["java","-jar","/Digital_Shop.jar"]