FROM gradle:jdk22 AS BUILD
WORKDIR /usr/app/
COPY . .

RUN gradle build
FROM openjdk:22-jdk-slim
COPY --from=BUILD /usr/app .
EXPOSE 8080
ENTRYPOINT exec java -jar build/libs/GS-Java-0.0.1-SNAPSHOT.jar