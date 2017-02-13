FROM openjdk:8-jre
ADD app/target/api-1.0-SNAPSHOT.jar /api.jar
ENTRYPOINT java -jar /api.jar

