FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ./target/car_rental_model-2.0.0.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
