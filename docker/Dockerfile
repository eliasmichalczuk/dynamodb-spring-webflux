FROM adoptopenjdk/openjdk11:alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY target/dynamodbspring-1.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]