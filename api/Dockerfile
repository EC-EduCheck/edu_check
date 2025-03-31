FROM openjdk:21-jdk-slim AS build

RUN apt-get update && apt-get install -y dos2unix

WORKDIR /app

COPY gradle gradle
COPY build.gradle settings.gradle gradlew ./

RUN chmod +x gradlew

RUN ./gradlew dependencies --no-daemon

COPY src src

RUN ./gradlew bootJar --no-daemon


FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Asia/Seoul /etc/localtime && \
    echo "Asia/Seoul" > /etc/timezone

ENV TZ=Asia/Seoul
ENV JAVA_OPTS="-Duser.timezone=Asia/Seoul"

EXPOSE 8080

CMD ["java", "-Duser.timezone=Asia/Seoul", "-jar", "app.jar"]