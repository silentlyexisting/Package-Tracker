FROM ubuntu

RUN apt update \
        && apt install default-jre -y \
        && apt install default-jdk -y \
        && apt install openjdk-11-jdk -y

COPY build/libs/code-0.0.1-SNAPSHOT.jar /home/code-0.0.1-SNAPSHOT.jar

WORKDIR /home

CMD ["java", "-jar", "/home/code-0.0.1-SNAPSHOT.jar"]

EXPOSE 7000
