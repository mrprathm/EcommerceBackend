# 🛒 E-Commerce Backend API

A **production-ready E-Commerce REST API** built with **Java Spring Boot**, **MySQL**, **JWT Authentication**, and **Swagger UI**.

---

## 🚀 Tech Stack

| Technology | Version |
|---|---|
| Java | 17 |
| Spring Boot | 3.2.3 |
| Spring Security + JWT | Latest |
| Spring Data JPA (Hibernate) | Latest |
| MySQL | 8.x |
| Lombok | Latest |
| Swagger (SpringDoc OpenAPI) | 2.3.0 |
| Maven | 3.x |

---

## 📁 Project Structure

```
EcommerceBackend/
├── src/main/java/com/ecommerce/
│   ├── config/           # Security & App Configuration
│   ├── controller/       # REST Controllers
│   ├── dto/              # Data Transfer Objects
│   ├── entity/           # JPA Entities
│   ├── exception/        # Global Exception Handling
│   ├── repository/       # JPA Repositories
│   ├── security/         # JWT Utils & Filters
│   └── service/          # Business Logic (Service + impl)
├── src/main/resources/
│   └── application.properties
└── pom.xml
```

---

## ⚙️ Setup & Run

### Prerequisites
- Java 17+
- MySQL 8.x running locally
- Maven 3.x
- IntelliJ IDEA (recommended)

### Step 1: Clone the repo
```bash
git clone https://github.com/YOUR_USERNAME/EcommerceBackend.git
cd EcommerceBackend
```

### Step 2: Configure MySQL
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db?createDatabaseIfNotExist=true
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### Step 3: Run the application
```bash
mvn spring-boot:run
```
Or open in **IntelliJ IDEA** → Run `EcommerceBackendApplication.java`

The app will start at: `http://localhost:8080`

---

## 📚 API Documentation (Swagger)

Once running, open: **http://localhost:8080/swagger-ui.html**

---

## 🔐 API Endpoints

### Auth
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login & get JWT token |

### Products (Public)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products` | Get all products (paginated) |
| GET | `/api/products/{id}` | Get product by ID |
| GET | `/api/products/search?keyword=` | Search products |
| GET | `/api/products/category/{categoryId}` | Get by category |

### Categories (Public)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/categories` | Get all categories |
| GET | `/api/categories/{id}` | Get by ID |

### Cart (Requires Auth)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/cart` | View cart |
| POST | `/api/cart/items` | Add item to cart |
| PUT | `/api/cart/items/{id}` | Update quantity |
| DELETE | `/api/cart/items/{id}` | Remove item |
| DELETE | `/api/cart` | Clear cart |

### Orders (Requires Auth)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/orders` | Place order from cart |
| GET | `/api/orders/my-orders` | Get my orders |
| GET | `/api/orders/{id}` | Get order by ID |

### Admin Only
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/products` | Create product |
| PUT | `/api/products/{id}` | Update product |
| DELETE | `/api/products/{id}` | Delete product |
| POST | `/api/categories` | Create category |
| GET | `/api/orders/admin/all` | Get all orders |
| PUT | `/api/orders/admin/{id}/status` | Update order status |

---

## 🔑 Authentication

Use **Bearer Token** authentication:
1. Register or Login to get JWT token
2. Add to headers: `Authorization: Bearer YOUR_TOKEN`

---

## 📊 Database Schema

- **users** - User accounts with roles (ADMIN/CUSTOMER)
- **categories** - Product categories
- **products** - Products with stock management
- **carts** - User shopping carts
- **cart_items** - Items in each cart
- **orders** - Customer orders
- **order_items** - Items in each order

---

## 👨‍💻 Author

Built with ❤️ using Spring Boot
