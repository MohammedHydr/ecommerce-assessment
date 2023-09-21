# Ecommerce Command

## Introduction

The Command Microservice is responsible for all write operations (POST, PUT, DELETE) in our e-commerce system, directly interacting with the PostgreSQL database. Built with Spring Boot and JPA, it ensures data integrity during high-load transactions.

Role in System:
1. Data Management: Handles all write transactions to the PostgreSQL database, ensuring data integrity.
2. Event Production: Emits events like ProductCreated, OrderUpdated, or CustomerDeleted to Kafka topics for system-wide consistency.

This microservice is a critical component for data management and event-driven updates in our e-commerce platform.

## Project Structure

This project structure is intentional and takes advantage of Spring Boot's conventions, as well as best practices for building scalable and maintainable applications.

1. Separation of Concerns: Each package has a specific role, making it easier to locate files and manage responsibilities. This separation is crucial for implementing the CQRS pattern effectively, as it keeps the command (write) operations isolated from other functionalities.

2. Event-Driven Architecture: The events and producers packages are specially designed to handle event sourcing, aligning with the event-driven nature of a CQRS-based system.

3. Modular Services: The service package contains business logic, cleanly separated from the HTTP request handling in controller and data access in repository. This modularization makes it easier to write unit tests and understand the business logic.

4. Error Handling: A dedicated exceptions package handles all kinds of exceptions, making the system robust and the codebase cleaner. The GlobalExceptionHandler.java allows us to handle exceptions centrally.

5. Data Integrity: The model package contains JPA entities that directly map to database tables, ensuring data integrity.

6. Configurations and Constants: Keeping all constants in a constants package and configurations in a configuration package streamlines the codebase and makes it easier to manage environment-specific settings.

7. Data Transfer Objects: The dto package holds classes that facilitate data transfer between different layers of the application, which is a common practice in complex systems to ensure data integrity and security.

8. Resource Management: The resources' directory houses the `application.properties` file, centralizing the application's configuration settings.


## Dependencies and Tools

In this project, the following dependencies and tools have been used to accomplish various tasks. The dependencies are managed through Maven, which is specified in the pom.xml file. Here's a brief overview:

### Tools
* Java 11: The programming language used for developing the application.
* Spring Boot 2.7.15: Framework used for quickly building production-ready applications.
* Maven: Dependency management and build tool.
* PostgreSQL: ACID-compliant relational database used for the command-side.
* Apache Kafka: Distributed event streaming platform used for Event Sourcing.

### Dependencies
1. **Spring Boot Web Starter:** This is the base starter that includes auto-configuration support, logging, and JSON support, enabling quick development of RESTful APIs.
```xml
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-web</artifactId>
```
2. **Apache Kafka Streams:** This is used for building applications and microservices, where the input and output data are stored in Kafka clusters.
```xml
<groupId>org.apache.kafka</groupId>
<artifactId>kafka-streams</artifactId>
```
3. **Spring Kafka:** Spring for Apache Kafka project; it provides a more "Spring-like" programming model for Kafka, including auto-configuration options.
```xml
<groupId>org.springframework.kafka</groupId>
<artifactId>spring-kafka</artifactId>
```
4. **PostgreSQL JDBC Driver:** Enables the Java application to connect with the PostgreSQL database.
```xml
<groupId>org.postgresql</groupId>
<artifactId>postgresql</artifactId>
<scope>runtime</scope>
```
5. **SpringFox:** Included for API documentation through Swagger UI.
```xml
<groupId>io.springfox</groupId>
<artifactId>springfox-boot-starter</artifactId>
<version>3.0.0</version>
```
6. **Lombok:** A library that helps to reduce boilerplate code in your Java classes.
```xml
<groupId>org.projectlombok</groupId>
<artifactId>lombok</artifactId>
<optional>true</optional>
```
7. **Spring Boot Starter Test:** This is used for unit testing Spring components with libraries that include JUnit, Hamcrest, and Mockito.
```xml
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-test</artifactId>
<scope>test</scope>
```
8. **Spring Kafka Test:** Provides some test utilities for Kafka.
```xml
<groupId>org.springframework.kafka</groupId>
<artifactId>spring-kafka-test</artifactId>
<scope>test</scope>
```
9. **Spring Data JPA:** Simplifies data access within the Spring application, providing powerful repository and custom object-mapping abstractions.
```xml
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-data-jpa</artifactId>
```

## Configuration

### KafkaProducerConfiguration

This configuration class is responsible for setting up the Kafka Producer in the application. It defines two key beans:

- **ProducerFactory**: Configures and creates a factory for producing Kafka events.
- **KafkaTemplate**: Provides a high-level abstraction for sending events to Kafka topics.
- **BOOTSTRAP_SERVERS_CONFIG**: This setting points to the Kafka server's address, here set as `localhost:9092`. It specifies where the producer will send the events.
- **KEY_SERIALIZER_CLASS_CONFIG**: Configured to use `StringSerializer`, this setting determines how the event keys will be serialized before they are sent to the Kafka topic.
- **VALUE_SERIALIZER_CLASS_CONFIG**: This is set to `JsonSerializer`, determining how the event values will be serialized. In this case, they will be converted to JSON format before being sent to Kafka.

### SpringFoxConfig

This class initializes the configuration for Swagger UI and the API documentation.

- **Docket Bean**: Customizes the Swagger output and API documentation settings.

### application.properties
- **Database Connection**:
    - `spring.datasource.url`: JDBC URL for the database.
    - `spring.datasource.username`: Username for the database.
    - `spring.datasource.password`: Password for the database.

- **Hibernate Configuration**:
    - `spring.jpa.properties.hibernate.dialect`: Sets the SQL dialect to use in Hibernate.
    - `spring.jpa.hibernate.ddl-auto`: Hibernate DDL auto settings.
    - `spring.jpa.properties.hibernate.jdbc.time_zone`: Configures the JDBC time zone.

- **Miscellaneous**:
    - `spring.mvc.pathmatch.matching-strategy`: Sets the path matching strategy for Spring MVC.

## Running the Service

## Running the Service

Follow these steps to run the service and its associated services:

### Prerequisites

- Ensure you have Java 11 or later installed.
- Download and install [pgAdmin4](https://www.pgadmin.org/download/) for PostgreSQL.
- Download and set up [Apache Kafka](https://kafka.apache.org/quickstart).
- Install [Postman](https://www.postman.com/downloads/) for API testing.

### Running Steps

1. **Spring Boot Application**: Navigate to the root directory of the Spring Boot application in your terminal and run:
    ```bash
    ./mvnw spring-boot:run
    ```
   Or if you have `mvn` installed:
    ```bash
    mvn spring-boot:run
    ```
   This will start the application on the default port 8080.

2. **PostgreSQL**: Open pgAdmin4 and connect to your PostgreSQL server.

3. **Database Connection**: The connection to the PostgreSQL database is configured in the `application.properties` file in the `resources` folder.

4. **Zookeeper and Kafka Server**:
    - Navigate to the Kafka download directory and run Zookeeper:
      ```bash
      .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
      ```
    - Then, start the Kafka server:
      ```bash
      .\bin\windows\kafka-server-start.bat .\config\server.properties
      ```

5. **Create Kafka Topics**: Still in the Kafka directory, run the following command to create the required topics:
    ```bash
    kafka-topics.bat --create --bootstrap-server localhost:9092 --topic test
    ```
   Replace `test` with the actual topic names like `ProductCreated`, `ProductUpdated`, etc.

6. **API Testing with Postman**: Open Postman to test the APIs (POST, PUT, DELETE) for operations on the `Customer`, `Order`, and `Product` databases.

7. **Event Triggering**: After performing write operations using the APIs, events will be triggered and sent to the Kafka topics for further processing or synchronization with the read database.
