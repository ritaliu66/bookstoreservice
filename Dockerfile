FROM openjdk:17.0.2-slim-buster

MAINTAINER rita

VOLUME /home

ADD bookstoreservice-0.0.1-SNAPSHOT.jar rita_bookstore.jar

RUN bash -c 'touch /rita_bookstore.jar'

ENTRYPOINT ["java","-jar","/rita_bookstore.jar"]

EXPOSE 8081
