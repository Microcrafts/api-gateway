FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
ARG CACHEBUST
COPY target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar
EXPOSE 8000
CMD ["java","-jar","api-gateway.jar"]