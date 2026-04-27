# 🛒 Rathod Store — E-Commerce Backend API

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-brightgreen?style=for-the-badge&logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)
![JWT](https://img.shields.io/badge/JWT-Auth-black?style=for-the-badge&logo=jsonwebtokens)
![Swagger](https://img.shields.io/badge/Swagger-UI-green?style=for-the-badge&logo=swagger)

**Production-ready E-Commerce REST API built with Java Spring Boot**

👨‍💻 Developed by **Pratham Rathod** | Full Stack Java Developer

[📚 API Docs (Swagger)](http://localhost:8080/swagger-ui.html) • [🔗 Frontend Repo](https://github.com/mrprathm/EcommerceFrontend)

</div>

---

## ✨ Features

- 🔐 **JWT Authentication** — Secure token-based login & registration
- 👥 **Role-Based Access** — ADMIN and CUSTOMER roles
- 🛍️ **Product Management** — CRUD with pagination, sorting & search
- 🗂️ **Category Management** — Organized product categories
- 🛒 **Shopping Cart** — Add, update, remove items in real-time
- 📦 **Order Management** — Place orders, track status
- 🔒 **BCrypt Encryption** — Passwords securely hashed
- 📚 **Swagger UI** — Interactive API documentation
- ⚡ **Auto Database Setup** — Tables + seed data on startup
- 🏗️ **Clean Architecture** — Controller → Service → Repository → DTO

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2.3 |
| Security | Spring Security + JWT |
| ORM | Spring Data JPA + Hibernate 6 |
| Database | MySQL 8.x |
| Build Tool | Maven |
| API Docs | Swagger UI (SpringDoc OpenAPI) |
| Utilities | Lombok, ModelMapper |

---

## 📁 Project Structure
src/main/java/com/ecommerce/
├── config/         # Security, CORS, Swagger, DataInitializer
├── controller/     # REST Controllers (Auth, Product, Cart, Order)
├── dto/            # Request/Response DTOs
├── entity/         # JPA Entities (User, Product, Order, Cart)
├── exception/      # Global Exception Handler
├── repository/     # JPA Repositories
├── security/       # JWT Utils & Filter
└── service/        # Business Logic

---

## ⚙️ Setup & Run

### Prerequisites
- Java 17+
- MySQL 8.x
- Maven 3.x

### Step 1: Clone
```bash
git clone https://github.com/mrprathm/EcommerceBackend.git
cd EcommerceBackend
```

### Step 2: Configure MySQL
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=your_password
```

### Step 3: Run
```bash
mvn spring-boot:run
```

### Step 4: Open Swagger UI
👉 http://localhost:8080/swagger-ui.html

---

## 🔑 Demo Login

| Role | Email | Password |
|---|---|---|
| **Admin** | prathamrathod200@gmail.com | Golu |

---

## 📊 API Endpoints

### Auth
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login & get JWT token |

### Products
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/products` | Get all (paginated) |
| GET | `/api/products/{id}` | Get by ID |
| GET | `/api/products/search?keyword=` | Search |
| POST | `/api/products` | Create (Admin) |
| PUT | `/api/products/{id}` | Update (Admin) |
| DELETE | `/api/products/{id}` | Delete (Admin) |

### Cart & Orders
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/cart` | View cart |
| POST | `/api/cart/items` | Add item |
| POST | `/api/orders` | Place order |
| GET | `/api/orders/my-orders` | My orders |

---

## 👨‍💻 Developer

**Pratham Rathod** — Full Stack Java Developer

📧 prathamrathod200@gmail.com | 📞 +91-9890394356 | 📍 Pune, Maharashtra

🎓 B.E. IT — D.Y. Patil College of Engineering, Pune (CGPA: 8.10)

**Skills:** Java | Spring Boot | React.js | MySQL | JWT | REST APIs | Hibernate

---

Built with ❤️ by **Pratham Rathod**
