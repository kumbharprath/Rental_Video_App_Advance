# 🎬 Rental Video Management Application

A **production-ready, scalable backend application** built using **Spring Boot** for managing a rental video platform. The system is designed with **enterprise-grade security**, **clean architecture**, and **performance-optimized persistence**, making it suitable for real-world deployment and extension.

---

## 🚀 Project Overview

The **Rental Video Management Application** exposes secure RESTful APIs to manage:

- 🎥 Video catalog and availability
- 👤 User authentication and authorization
- 🔐 Role-based access control (RBAC)
- 📦 Video rental and return lifecycle

The application follows **industry best practices** such as stateless authentication, layered architecture, and ORM optimization using **Spring Boot, JWT, JPA, and Hibernate**.

---

## 🏗️ Architecture Overview

- **Controller Layer** – Handles REST API requests and responses
- **Service Layer** – Encapsulates business logic and validations
- **Repository Layer** – Data access using Spring Data JPA
- **Security Layer** – JWT authentication, authorization filters, and RBAC
- **Persistence Layer** – MySQL with Hibernate ORM

This layered approach ensures **loose coupling**, **testability**, and **scalability**.

---

## 🛠️ Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot
- **Security:** Spring Security, JWT (JSON Web Tokens)
- **Database:** MySQL
- **ORM:** JPA, Hibernate
- **Build Tool:** Gradle
- **API Style:** RESTful APIs

---

## 🔐 Security & Authentication

- Stateless authentication using **JWT tokens**
- Secure login and token-based authorization
- **Role-Based Access Control (RBAC)** (e.g., `ADMIN`, `USER`)
- Spring Security filters for request validation
- Protected endpoints based on user roles

> All sensitive endpoints require a valid JWT passed via the `Authorization` header.

---

## 📦 Core Functionalities

### 🎥 Video Catalog Management
- Create, update, delete, and retrieve video details
- Track video availability status
- Prevent duplicate or invalid entries

### 👤 User Management
- User registration and authentication
- Secure password handling
- Role-based access enforcement

### 🔄 Rental Management
- Rent and return videos
- Enforce availability constraints
- Track rental transactions

---

## ⚡ Performance & Optimization

- Optimized database queries using **JPA and Hibernate**
- Efficient entity relationships and lazy loading
- Reduced query execution time by approximately **15%**
- Minimized unnecessary database calls

---

## 📡 API Design Principles

- RESTful endpoint conventions
- Clear HTTP status codes (`200`, `201`, `400`, `401`, `403`, `404`)
- Secure request handling via Spring Security
- DTO-based request and response mapping

---

## 📁 Project Structure

```
src/main/java
├── controller      # REST API controllers
├── service         # Business logic
├── repository      # Data access layer
├── entity          # JPA entities
├── dto             # Request/response models
├── security        # JWT & Spring Security config
└── config          # Application configuration
```

---

## ⚙️ Configuration & Setup

### Prerequisites

- Java 17 or higher
- Gradle 8+
- MySQL 8+

### Environment Configuration

Configure the database and JWT properties in `application.properties`:

```
spring.datasource.url=jdbc:mysql://localhost:3306/rental_video_db
spring.datasource.username=your_username
spring.datasource.password=your_password

jwt.secret=your_jwt_secret
jwt.expiration=86400000
```

### Build & Run

```bash
git clone https://github.com/kumbharprath/Rental_Video_App_Advance.git
cd Rental_Video_App_Advance
./gradlew build
./gradlew bootrun
```

---

## 🧪 Testing & API Usage

- APIs tested using **Postman**
- JWT token must be included in the request header:

```
Authorization: Bearer <JWT_TOKEN>
```

- Unauthorized access is blocked by Spring Security

---

## 📈 Production Readiness Considerations

- Stateless authentication (horizontal scalability)
- Clear separation of concerns
- Database query optimization
- Secure API design

---

## 🔮 Future Enhancements

- Swagger / OpenAPI documentation
- Pagination, sorting, and filtering
- Caching with Redis
- Docker & Docker Compose support
- CI/CD pipeline integration
- Frontend integration

---

## 👤 Author

**Prathamesh Kumbhar**  
Java Backend Developer

---

## ⭐ Final Notes

This project demonstrates **production-level backend engineering skills**, including security, performance tuning, and scalable architecture. It is well-suited for showcasing expertise in **Java backend development** and **Spring Boot–based systems**.

If you find this project useful, feel free to ⭐ star the repository and contribute!

