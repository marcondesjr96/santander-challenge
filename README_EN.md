# 💳 Transactions API

## 📘 Description

The **Transactions API** is a platform for managing financial transactions, account balances, and transaction history.
Developed with Java 17 and Spring Boot, the application follows Clean Architecture principles and the CQRS (Command Query Responsibility Segregation) pattern, ensuring high cohesion, low coupling, and maintainability.

## 🛠️ Technologies Used
- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Hibernate**
- **PostgreSQL**
- **Redis**
- **Lombok**
- **JWT (JSON Web Token)**
- **Maven**

## 🚀 Main Features

### 1. Transaction Management

- Registration of financial transactions.
- Association of transactions with accounts and users.
- Automatic balance update after each operation

### 2. Balance and History Queries

- Obtaining the current balance of an account.
- Use of Redis cache for fast and scalable responses.

### 3. Authentication and Security

- JWT-based authentication.
- Access control per authenticated user.
- Status integration between governance systems and UH (UhStatusIntegration).

### 4. Redis Integration

- Caching of BalanceResponse and TransactionResponse.
- Reduction of repetitive database queries.

## 🧱 Architecture

The application follows Clean Architecture principles, divided into independent and testable layers:

- **Adapters**: REST controllers, DTOs and Mappers.
- **Application**: Use cases (Commands and Queries) that contain orchestration logic.
- **Domain**: Entities and pure business rules.
- **Infrastructure**: Configurations, repositories, cache and security.

## CQRS Pattern

## 🗃️ Database

- **Commands**: Execute write operations (e.g.: create transaction).
- **Queries**: Execute read operations (e.g.: query balance).

## ▶️ How to Run the Project

### Prerequisites
- Docker and Docker Compose installed
- Postman (to test endpoints)

### 1. **Clone the repository:**
```bash
git clone https://github.com/marcondesjr96/santander-challenge.git
cd santander-challenge
```

### 2. **Start the entire stack with Docker Compose:**
```bash
docker-compose up -d
```

**This command will:**
- Create and start the PostgreSQL container on port 5432
- Create and start the Redis container on port 6379
- Compile and start the Spring Boot application on port 8080
- Automatically configure the network between containers

### 3. **Check if services are running:**
```bash
docker ps
```

**You should see 3 active containers:**
- `transactions-api` (application)
- `postgres-db` (database)
- `redis-db` (cache)

## 🧪 Testing the Endpoints

### Import Collection in Postman
1. Open Postman
2. Click "Import"
3. Select the file `SantanderChallenge.postman_collection.json`
4. The collection will be imported with all endpoints configured

### 🔐 JWT Authentication

**IMPORTANT:** After login, all authenticated requests use the JWT token to identify the user and their associated account. It is not necessary to pass additional data such as user ID or account - this information is automatically extracted from the token.

**How it works:**
1. The JWT token contains user information (ID, login, etc.)
2. The application decodes the token and automatically identifies the associated account
3. All operations (deposit, withdrawal, query) are performed on the authenticated user's account

### Recommended Test Flow

#### 1. **Create User**
- **Endpoint:** `POST /api/v1/users/register`
- **Description:** Creates a new user and automatically an associated account
- **Body example:**
```json
{
    "fullName": "John Silva",
    "cpf": "123.456.789-00",
    "login": "john.silva",
    "password": "mypassword123"
}
```

#### 2. **Login**
- **Endpoint:** `POST /api/v1/auth/login`
- **Description:** Authenticates the user and returns a JWT token
- **Body example:**
```json
{
    "login": "john.silva",
    "password": "mypassword123"
}
```
- **⚠️ IMPORTANT:** The JWT token will be automatically saved in the Postman variable `{{token}}`

#### 3. **Deposit Money**
- **Endpoint:** `POST /api/v1/accounts/deposit`
- **Description:** Adds value to the account balance
- **Authentication:** Bearer Token (automatic via `{{token}}`)
- **⚠️ IMPORTANT:** User and account data are automatically obtained from the JWT token
- **Body example:**
```json
{
    "amount": 500.00
}
```

#### 4. **Check Balance**
- **Endpoint:** `GET /api/v1/accounts/balance`
- **Description:** Returns current balance and transaction history (with Redis cache)
- **Authentication:** Bearer Token (automatic via `{{token}}`)
- **⚠️ IMPORTANT:** User and account data are automatically obtained from the JWT token

#### 5. **Pay Bill**
- **Endpoint:** `POST /api/v1/accounts/pay`
- **Description:** Debits value from the account balance
- **Authentication:** Bearer Token (automatic via `{{token}}`)
- **⚠️ IMPORTANT:** User and account data are automatically obtained from the JWT token
- **Body example:**
```json
{
    "amount": 150.00
}
```

### 🔍 Monitoring and Logs

#### View application logs:
```bash
docker logs -f transactions-api
```

#### Check data in PostgreSQL:
```bash
docker exec -it postgres-db psql -U postgres -d santander_db
```

#### Check cache in Redis:
```bash
docker exec -it redis-db redis-cli
# Useful commands:
# KEYS * (list all keys)
# GET <key> (get value of a key)
```

### 🛑 Stop Services
```bash
docker-compose down
```

### 🔄 Rebuild (if necessary)
```bash
docker-compose down
docker-compose up --build -d
```

## 🌐 Important URLs

- **Application:** http://localhost:8080
- **PostgreSQL:** localhost:5432 (user: postgres, password: postgres)
- **Redis:** localhost:6379

## 📋 Notes for Evaluation

1. **Clean Architecture:** The project follows Clean Architecture principles with clear separation of responsibilities
2. **CQRS:** Implementation of the Command Query Responsibility Segregation pattern
3. **Redis Cache:** Functional integration with Redis for query optimization
4. **JWT Security:** Stateless authentication with JWT tokens
5. **Docker:** Complete containerization of application and dependencies
6. **Tests:** Complete Postman collection for endpoint validation