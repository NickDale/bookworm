FROM maven:3.6-openjdk-11 AS compiler
MAINTAINER  Balogh Norbert <balogh.norbert92@gmail.com>

WORKDIR /usr/src/app
COPY . .

RUN mvn -B clean package -Dmaven.test.skip=true

FROM openjdk:11-slim

WORKDIR /opt/app

EXPOSE 9999

COPY --from=compiler /usr/src/app/target/library-*.jar /opt/app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
