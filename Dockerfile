FROM eclipse-temurin:latest

VOLUME /tmp

ENV JAVA_OPTS=" -Xms512m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

WORKDIR app

COPY ./target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
