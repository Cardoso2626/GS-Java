# Etapa de build
FROM gradle:8.7.0-jdk22 AS BUILD
WORKDIR /usr/app
COPY . .

RUN ./gradlew build -x test

# Etapa de runtime
FROM openjdk:22-jdk-slim
WORKDIR /app

COPY --from=BUILD /usr/app/build/libs/GSJava-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
