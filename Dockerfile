FROM openjdk:21
COPY target/jarFactory-0.0.1-SNAPSHOT.jar /java/jarFactory.jar
WORKDIR /java
CMD ["java", "-jar", "app.jar"]