# 🏗️ Architectural Evaluation Instructions

## 📋 Evaluation Checklist

### ✅ Project Execution
1. **Start the complete stack:**
   ```bash
   docker-compose up -d
   ```

2. **Verify that all containers are running:**
   ```bash
   docker ps
   ```
   Should show: `transactions-api`, `postgres-db`, `redis-db`

### 🧪 Postman Tests
1. Import `SantanderChallenge.postman_collection.json`
2. Execute in sequence:
   - **Create User** → `POST /api/v1/users/register`
   - **Login** → `POST /api/v1/auth/login` (token saved automatically)
   - **Deposit** → `POST /api/v1/accounts/deposit`
   - **Check Balance** → `GET /api/v1/accounts/balance`
   - **Pay Bill** → `POST /api/v1/accounts/pay`

## 🏛️ Architectural Points for Evaluation

### 1. **Clean Architecture**
```
src/main/java/com/santander/challenge/transactions/
├── adapters/           # Interface Adapters
│   ├── controller/     # REST Controllers
│   ├── dto/           # Data Transfer Objects
│   └── mapper/        # Mappers between layers
├── application/        # Application Business Rules
│   ├── command/       # Write Use Cases (CQRS)
│   └── query/         # Read Use Cases (CQRS)
├── domain/            # Enterprise Business Rules
│   ├── model/         # Domain entities
│   ├── repository/    # Repository interfaces
│   └── exception/     # Domain exceptions
└── infrastructure/    # Frameworks & Drivers
    ├── configuration/ # Spring configurations
    ├── persistence/   # JPA implementation
    ├── security/      # JWT and Spring Security
    └── cache/         # Redis implementation
```

### 2. **CQRS Pattern (Command Query Responsibility Segregation)**
- **Commands:** `DepositMoneyUseCase`, `PayBillUseCase`, `RegisterUserUseCase`
- **Queries:** `GetBalanceAndHistoryUseCase`
- Clear separation between write and read operations

### 3. **Redis Integration**
- **Query cache:** `BalanceResponse` is cached in Redis
- **Configuration:** `RedisConfig.java` with `LettuceConnectionFactory`
- **Implementation:** `RedisCacheService.java`
- **Health Check:** Endpoint `/api/v1/health/redis` to verify connectivity

### 4. **JWT Security**
- **Authentication:** `JwtAuthenticationFilter.java`
- **Token generation:** `JwtService.java`
- **Configuration:** `SecurityConfig.java` with public and protected endpoints

### 5. **Persistence**
- **PostgreSQL:** Main database with JPA/Hibernate
- **Entities:** `UserEntity`, `AccountEntity`, `TransactionEntity`
- **Repositories:** Spring Data JPA implementation

## 🔍 Technical Attention Points

### Performance
- ✅ Redis cache implemented for balance queries
- ✅ Lazy loading configured in JPA
- ✅ Connection pooling via HikariCP (Spring Boot default)

### Security
- ✅ JWT stateless authentication
- ✅ Passwords hashed with BCrypt
- ✅ CORS configured
- ✅ CSRF disabled (REST API)

### Scalability
- ✅ Stateless architecture
- ✅ Distributed cache (Redis)
- ✅ Docker containerization
- ✅ Separation of concerns (CQRS)

### Observability
- ✅ Structured logs
- ✅ Custom health checks
- ✅ Metrics via Spring Actuator

## 🧪 Advanced Test Scenarios

### Redis Cache Test
1. Login and check balance (first time - goes to database)
2. Check balance again (second time - comes from cache)
3. Verify in Redis:
   ```bash
   docker exec -it redis-db redis-cli
   KEYS *
   GET "balance:user:<user-id>"
   ```

### Transaction Test
1. Deposit $1000
2. Pay $300
3. Check balance (should show $700)
4. Verify transaction history

### Security Test
1. Try to access protected endpoints without token (should return 403)
2. Use expired token (should return 401)
3. Try to create user with duplicate CPF (should return error)

## 📊 Quality Metrics

- **Test Coverage:** Check in `src/test/java/`
- **Layer Separation:** Unidirectional dependencies
- **SOLID Principles:** Applied throughout the architecture
- **DDD:** Rich and well-defined domain entities

## 🚀 Next Steps (Suggestions)

1. **Monitoring:** Integration with Prometheus/Grafana
2. **Documentation:** Swagger/OpenAPI
3. **Testing:** Integration tests with Testcontainers
4. **CI/CD:** Automated pipeline
5. **Kubernetes:** Manifests for cluster deployment

---

**💡 Tip:** For a complete evaluation, run all Docker commands and Postman endpoints. The project is configured to work "out of the box" with `docker-compose up -d`.
