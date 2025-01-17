FROM openjdk:17-jdk-alpine


WORKDIR /app


COPY target/back_end-0.0.1-SNAPSHOT.jar /app/demo.jar


EXPOSE 8080


ENTRYPOINT ["java", "-jar", "/app/demo.jar"]