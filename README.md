# 4.01-Spring-Introduction

## 📄 Description

This repository contains a simple Spring Boot REST API developed as a learning exercise to practice the fundamentals of backend development with Java.
The application exposes several REST endpoints to manage a list of users in memory (no database), allowing you to:
- Create users
- List all users
- Retrieve a user by ID
- Filter users by name

The application has been refactored to follow **SOLID principles**:

- **Controller** -> handles HTTP requests and delegates to the service.
- **Service** -> contains business logic, validations and rules.
- **Repository** -> abstract data access (in-memory list)

The project includes: 

- Unit tests for the service layer using **Mockito**
- Integration tests for the controller layer using **MockMvc**
- Global exception handling with `@ControllerAdvice`

## 💻 Technologies used

- Java 17+
- Spring Boot
- Maven
- JUnit 5
- Spring Boot Test (MockMvc)
- Mockito
- Jackson (JSON serialization)
- IntelliJ IDEA
- Postman

## 📋 Requirements

- Java 17 or higher
- Maven (IntelliJ bundled Maven is sufficient)
- IDE capable of running Spring Boot projects (IntelliJ IDEA, Eclipse, etc.)

## 🛠️ Installation

1. Clone the repository:

```bash
git clone https://github.com/ccasro/4.01-Spring-Introduction-level3/
```

2. Open the project in your IDE (e.g., IntelliJ IDEA)
3. Ensure Maven dependencies are downloaded automatically

## ▶️ Execution

Run the application by executing the main Spring Boot class:

UserApiApplication.java

The application will start on:

http://localhost:9000

## 🧪 Testing

The service layer is tested with Mockito:

- Business rules like unique email validation
- User creation logic
- Repository interactions are mocked

Controller endpoints are tested end-to-end using MockMvc and @SpringBootTest:

- Verify empty user list initially
- User creation with generated UUID
- Conflict response for duplicate emails
- Get user by ID
- 404 for non-existent users
- Filtering users by name
- Handling blank name query parameter

```bash
mvn test
```

## 🌐 Deployment
No production deployment is required for this exercise

## 🤝 Contributions

- Use the main branch for development.
- Make small, frequent commits following Conventional Commits.
- Do not add compiled files or credentials to the repository.
- To propose improvements, create a branch and open a pull request.

## 📌 Notes

This project focuses on:

* Layered architecture (Controller → Service → Repository)
* REST API design
* JSON request/response handling
* UUID-based identifiers
* Unit and integration testing
* Exception handling with @ControllerAdvice