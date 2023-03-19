FROM openjdk:17 as build
MAINTAINER berkaydemirel.dev
COPY target/couriertracking-0.0.1-SNAPSHOT.jar courier-tracking.jar
ENTRYPOINT ["java","-jar","/courier-tracking.jar"]