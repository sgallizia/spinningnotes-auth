
# JWT token based authentication

## Description

This is a REST application, developed with Spring Boot, which allows a JWT token based authentication with the following functionalities:

* sign-up
* sign-in
* token validation

The users are stored on a MongoDB database.

## Requirements

You must have a running MongoDB instance.

## Configuration

For a local deployment, you can modify the application.yml file.
For a Kubertenes deployment, you can create a ConfigMap that has a metadata.name value of the name of your Spring application as defined by the spring.application.name property.
This ConfigMap will override the properties of the default application.yml file.
This is an example of a Kubernetes ConfigMap yaml file:

```
apiVersion: v1
kind: ConfigMap
metadata:
  name: <your application name>
  namespace: <your Kubernetes namespace>
data:
  application.yaml: |-
    app:
      token:
        secretkey: ${TOKEN_SECRETKEY}
        issuer: <your custom issuer value>
    spring:
      data:
        mongodb:
          host: mongodb-service
    server:
      port: 8080
```

TOKEN_SECRETKEY is the environment variable which contains the token secret key.

## Usage

To run the application, run the following command:

```
mvnw spring-boot:run
```


