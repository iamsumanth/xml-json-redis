FROM gradle:6.8.2-jdk11 as builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar

FROM openjdk:11-jre-slim
EXPOSE 8080
COPY --from=builder /home/gradle/src/build/libs/xml-to-json-1.0-SNAPSHOT.jar /app/
WORKDIR /app
CMD ["java", "-jar", "xml-to-json-1.0-SNAPSHOT.jar"]