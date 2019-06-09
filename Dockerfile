FROM openjdk:8-jdk-alpine
ARG VERSION
COPY target/booking-quote-srv-${VERSION}.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
