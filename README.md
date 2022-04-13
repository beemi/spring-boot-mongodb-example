# spring-boot-mongodb-example
Spring boot service with mongoDB CRUD example


## Description
This is a simple example of a Spring boot service with mongoDB CRUD.

## Running MongoDB as a Docker Container

You can start a MongoDB container using Docker with the following command:

```shell
docker run -d -p 27017:27017 --name mongodb mongo
```

## Gradle build

```shell
gradle bootRun
```

## Access actuator

http://localhost:8080/actuator

http://localhost:8080/actuator/health

## Swagger UI

http://localhost:8080/api-docs/

http://localhost:8080/swagger-ui/index.html

![Alt text](docs/img.png)

## Prometheus

http://localhost:8080/actuator/prometheus

![Alt text](docs/img_1.png)


## Contacts
Owner: [beemi.raja@gmail.com](beemi.raja@gmail.com)