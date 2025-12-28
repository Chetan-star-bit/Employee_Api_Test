# Employee API (Spring Boot)

## Overview
This is a **Java Spring Boot–based Employee Management REST API** that provides CRUD operations for employee data.
The application is built using **Spring Boot, Spring Data JPA, and Docker**, following a clean **layered architecture**
with centralized exception handling.

---

## Technology Stack
- **Language:** Java
- **Framework:** Spring Boot
- **Build Tool:** Maven (Maven Wrapper included)
- **Database:** JPA / Hibernate (configurable)
- **Containerization:** Docker & Docker Compose
- **Architecture:** Layered (Controller → Service → Repository)
- **Exception Handling:** Global Exception Handler

---

## Project Structure
```
Employee_Api-main/
│
├── pom.xml
├── Dockerfile
├── docker-compose.yml
├── mvnw / mvnw.cmd
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/dattta/employeeapi/
│   │   │       ├── EmployeeapiApplication.java
│   │   │
│   │   │       ├── controller/
│   │   │       │   └── EmployeeController.java
│   │   │
│   │   │       ├── service/
│   │   │       │   └── EmployeeService.java
│   │   │
│   │   │       ├── repository/
│   │   │       │   └── EmployeeRepository.java
│   │   │
│   │   │       ├── model/
│   │   │       │   └── Employee.java
│   │   │
│   │   │       ├── exception/
│   │   │       │   ├── EmployeeNotFoundException.java
│   │   │       │   └── EmployeeAlreadyExistsException.java
│   │   │
│   │   │       └── ExceptionHandler/
│   │   │           ├── GlobalExceptionHandler.java
│   │   │           └── ErrorResponse.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/com/dattta/employeeapi/
│           └── EmployeeapiApplicationTests.java
```

---

## Application Architecture (Deep Understanding)

### 1. Controller Layer
- **EmployeeController**
  - Exposes REST endpoints for employee operations
  - Handles HTTP requests and responses
  - Delegates business logic to the service layer

---

### 2. Service Layer
- **EmployeeService**
  - Contains core business logic
  - Validates employee data
  - Handles create, read, update, and delete operations

---

### 3. Repository Layer
- **EmployeeRepository**
  - Extends Spring Data JPA repository
  - Handles database interactions

---

### 4. Model Layer
- **Employee**
  - Represents the employee entity
  - Mapped to the database table using JPA annotations

---

### 5. Exception Handling
- Centralized error handling using:
  - `@ControllerAdvice`
  - Custom exception classes
- Ensures consistent error responses using `ErrorResponse`

---

## Configuration
All application configuration is managed via:
```
src/main/resources/application.properties
```

Includes:
- Server port
- Database configuration
- JPA/Hibernate settings

---

## How to Run the Application

### Using Maven Wrapper
```bash
./mvnw spring-boot:run
```

Windows:
```bash
mvnw.cmd spring-boot:run
```

---

### Using Docker
```bash
docker-compose up --build
```

---

## API Endpoints (High-Level)
- `GET    /employees` – Get all employees
- `GET    /employees/{id}` – Get employee by ID
- `POST   /employees` – Create new employee
- `PUT    /employees/{id}` – Update employee
- `DELETE /employees/{id}` – Delete employee

---

## Future Enhancements
- Pagination & sorting
- Validation using Bean Validation
- Authentication & authorization
- Swagger/OpenAPI documentation
- Cloud deployment support

---

## Author
**Dattatray Narhe**  
Software Developer


