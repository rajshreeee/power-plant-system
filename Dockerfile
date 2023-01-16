FROM maven:3.8.6-eclipse-temurin-17 AS MVNBUILDER
WORKDIR /
COPY pom.xml .
COPY ./src ./src
RUN mvn clean install

FROM eclipse-temurin:17-jre-focal
ARG JAR_FILE=target/*.jar
COPY --from=MVNBUILDER ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]