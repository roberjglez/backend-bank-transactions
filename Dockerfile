FROM openjdk:8-jre
EXPOSE 8080
ADD target/backend-bank-transactions-0.0.1-SNAPSHOT.jar backend-bank-transactions-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "backend-bank-transactions-0.0.1-SNAPSHOT.jar"]
