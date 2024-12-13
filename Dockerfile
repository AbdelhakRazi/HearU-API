FROM eclipse-temurin:17-jdk-alpine

VOLUME /tmp

ENV JAVA_OPTS " -Xms512m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

WORKDIR application

COPY ./target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]