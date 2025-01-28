FROM openjdk:17
COPY target/payment-orchestrator-0.0.1-SNAPSHOT.jar payment-orchestrator.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/payment-orchestrator.jar"]
