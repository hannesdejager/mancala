FROM openjdk:8-jre-alpine
LABEL maintainer="Hannes de Jager <hannes.de.jager@gmail.com>"

RUN mkdir /mancala

# Add the fat jar
COPY target/mancala-1.0-SNAPSHOT.jar /mancala/mancala.jar

# Yeah lets work from here
WORKDIR /mancala

# HTTP port
EXPOSE 8080

# This runs our game
CMD ["java", "-jar", "mancala.jar"]
