# Objective

- [x] Creates CRUD APIs for Vehicles
- [x] Uses MongoDB
- [x] Allow both XML/JSON
- [x] Passes 91% test coverage
- [x] Uses TestContainers for MongoDB

# APIs

- Postman Collection [Under Project Root Dir/ioMoto.postman_collection.json](/ioMoto.postman_collection.json)
- Swagger OpenAPI Collection [Under Project Root Dir/swagger-open-api.yaml](/swagger-open-api.yaml)

# Getting started

The module was implemented/tested on Java 11

```bash
$ java --version
openjdk 11.0.11 2021-04-20
OpenJDK Runtime Environment AdoptOpenJDK-11.0.11+9 (build 11.0.11+9)
OpenJDK 64-Bit Server VM AdoptOpenJDK-11.0.11+9 (build 11.0.11+9, mixed mode)
```

## Change Database Production/Development

The testing uses TestContainers to avoid 
writing to the production/staging database.

The database settings can be overriden in
[src/main/resources/application.yaml](src/main/resources/application.yaml)

## Run App

```bash
./gradlew bootRun
```

This command will start the server on port `8080`

## Run Tests

```bash
./gradlew test
```

## Run Coverage Verification

```bash
./gradlew jacocoTestCoverageVerification
```

## Generate Coverage HTML Reports

```bash
./gradlew jacocoTestReport
```

Reports are available in

* [build/testReports/index.html](build/testReports/index.html)
* [build/reports/tests/test/index.html](build/reports/tests/test/index.html)

# Directory Structure

<pre>
.
├── main
│   ├── java
│   │   └── com
│   │       └── iomoto
│   │           ├── demo
│   │           │   ├── configs
│   │           │   │   ├── Config.java
│   │           │   │   └── MongoValidationConfig.java
│   │           │   ├── controllers
│   │           │   │   ├── GlobalExceptionHandlerController.java
│   │           │   │   └── VehicleController.java
│   │           │   ├── exceptions
│   │           │   │   ├── CustomError.java
│   │           │   │   └── ResourceNotFoundException.java
│   │           │   ├── models
│   │           │   │   └── VehicleModel.java
│   │           │   ├── repositories
│   │           │   │   └── VehicleRepository.java
│   │           │   └── services
│   │           │       └── VehicleService.java
│   │           └── DemoApplication.java
│   └── resources
│       └── application.yaml
└── test
    └── java
        └── com
            └── iomoto
                └── demo
                    ├── DemoApplicationTests.java
                    └── vehicles
                        ├── VehicleControllerTest.java
                        └── VehicleServiceTest.java


</pre>