# Courier Tracking

## INTRODUCTION

CourierTracking is an online market project. The project has couriers and stores. It saves the courier's geolocations
and calculates the total distance. It follows the couriers around the stores.

## REQUIREMENTS

    JDK version 17
    Spring Boot version 2.7.9

## TECH STACK

    Java
    Spring Boot
    Spring Data
    Spring Logging
    Spring Validation
    Spring Test
    JUnit
    Mockito
    H2 Database
    Log4j
    Lombok
    Docker

## RUN USING DOCKER

You can start the project with docker.

    mvn clean package
    docker compose up reading-is-good

## POSTMAN COLLECTION

    You can use postman collection from 'resources/Courier Tracking.postman_collection.json'

## DATABASE

    You can use the H2-Console for exploring the database under http://localhost:8080/h2-console
    It has init data in 'resources/data.sql'

## STORE

    You can use the store API for creating, deleting and getting stores. 'api/v1/stores'
    The stores are saved in the store table.

## COURIER

    You can use the courier API for creating, deleting and getting couriers. 'api/v1/courier'
    The couriers are saved in the courier table.

## COURIER GEOLOCATION

    You can use the courier geolocation API for creating, deleting and getting couriers. 'api/v1/couriers/{courierId}/geolocations'
    The courier geolocations are saved in the courier_geolocation table.

## COURIER TRACKING

    The courier tracking logs are saved in the courier_tracking table. These records are the logs of the couriers around the store.

## RUN TEST

    If you want to run tests all together, you can use the commands below
    
    1-) mvn test : Run tests
    2-) mvn clean install -Dit : Clears the target directory and builds the project and installs the resulting artifact (JAR) with unit and integration tests
    3-) mvn clean package -Dmaven.test.skip=true : Clears the target directory and builds the project and packages the resulting JAR file into the target directory
       without running the unit tests during the build.
    
    if you want to run tests one by one, you can use run button of your own ide in test class. 
    
