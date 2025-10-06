# 🏗️ Instruções para Avaliação Arquitetural

## 📋 Checklist de Avaliação

### ✅ Execução do Projeto
1. **Subir a stack completa:**
   ```bash
   docker-compose up -d
   ```

2. **Verificar se todos os containers estão rodando:**
   ```bash
   docker ps
   ```
   Deve mostrar: `transactions-api`, `postgres-db`, `redis-db`


### 🧪 Testes com Postman
1. Importar `SantanderChallenge.postman_collection.json`
2. Executar na sequência:
   - **Criar Usuário** → `POST /api/v1/users/register`
   - **Login** → `POST /api/v1/auth/login` (token salvo automaticamente)
   - **Depositar** → `POST /api/v1/accounts/deposit`
   - **Consultar Saldo** → `GET /api/v1/accounts/balance`
   - **Pagar Conta** → `POST /api/v1/accounts/pay`

## 🏛️ Pontos Arquiteturais para Avaliação

### 1. **Clean Architecture**
```
src/main/java/com/santander/challenge/transactions/
├── adapters/           # Interface Adapters
│   ├── controller/     # REST Controllers
│   ├── dto/           # Data Transfer Objects
│   └── mapper/        # Mappers entre camadas
├── application/        # Application Business Rules
│   ├── command/       # Use Cases de escrita (CQRS)
│   └── query/         # Use Cases de leitura (CQRS)
├── domain/            # Enterprise Business Rules
│   ├── model/         # Entidades de domínio
│   ├── repository/    # Interfaces de repositório
│   └── exception/     # Exceções de domínio
└── infrastructure/    # Frameworks & Drivers
    ├── configuration/ # Configurações Spring
    ├── persistence/   # Implementação JPA
    ├── security/      # JWT e Spring Security
    └── cache/         # Implementação Redis
```

### 2. **Padrão CQRS (Command Query Responsibility Segregation)**
- **Commands:** `DepositMoneyUseCase`, `PayBillUseCase`, `RegisterUserUseCase`
- **Queries:** `GetBalanceAndHistoryUseCase`
- Separação clara entre operações de escrita e leitura

### 3. **Integração Redis**
- **Cache de consultas:** `BalanceResponse` é cacheado no Redis
- **Configuração:** `RedisConfig.java` com `LettuceConnectionFactory`
- **Implementação:** `RedisCacheService.java`

### 4. **Segurança JWT**
- **Autenticação:** `JwtAuthenticationFilter.java`
- **Geração de tokens:** `JwtService.java`
- **Configuração:** `SecurityConfig.java` com endpoints públicos e protegidos

### 5. **Persistência**
- **PostgreSQL:** Banco principal com JPA/Hibernate
- **Entidades:** `UserEntity`, `AccountEntity`, `TransactionEntity`
- **Repositórios:** Implementação Spring Data JPA

## 🔍 Pontos de Atenção Técnica

### Performance
- ✅ Cache Redis implementado para consultas de saldo
- ✅ Lazy loading configurado no JPA
- ✅ Connection pooling via HikariCP (padrão Spring Boot)

### Segurança
- ✅ JWT stateless authentication
- ✅ Senhas hasheadas com BCrypt
- ✅ CSRF desabilitado (API REST)

### Escalabilidade
- ✅ Arquitetura stateless
- ✅ Cache distribuído (Redis)
- ✅ Containerização Docker
- ✅ Separação de responsabilidades (CQRS)

### Observabilidade
- ✅ Logs estruturados
- ✅ Health checks customizados
- ✅ Métricas via Spring Actuator

## 🧪 Cenários de Teste Avançados

### Teste de Cache Redis
1. Fazer login e consultar saldo (primeira vez - vai ao banco)
2. Consultar saldo novamente (segunda vez - vem do cache)
3. Verificar no Redis:
   ```bash
   docker exec -it redis-db redis-cli
   KEYS *
   GET "balance:user:<user-id>"
   ```

### Teste de Transações
1. Depositar R$ 1000
2. Pagar R$ 300
3. Consultar saldo (deve mostrar R$ 700)
4. Verificar histórico de transações

### Teste de Segurança
1. Tentar acessar endpoints protegidos sem token (deve retornar 403)
2. Usar token expirado (deve retornar 401)
3. Tentar criar usuário com CPF duplicado (deve retornar erro)

## 📊 Métricas de Qualidade

- **Cobertura de Testes:** Verificar em `src/test/java/`
- **Separação de Camadas:** Dependências unidirecionais
- **Princípios SOLID:** Aplicados em toda a arquitetura
- **DDD:** Entidades de domínio ricas e bem definidas
---

**💡 Dica:** Para uma avaliação completa, execute todos os comandos Docker e endpoints Postman. O projeto está configurado para funcionar "out of the box" com `docker-compose up -d`.
