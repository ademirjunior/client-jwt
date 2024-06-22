FROM maven:3.8.4-jdk-21 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xm clean package -Dmaven.test.skip


FROM openjdk:21-jre-alpine
EXPOSE 8080
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]