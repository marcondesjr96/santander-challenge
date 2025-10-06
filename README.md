# 💳 Transactions API

## 📘 Descrição

A **Transactions API** é uma plataforma para gerenciamento de transações financeiras, saldos de contas e histórico de movimentações.
Desenvolvida com Java 17 e Spring Boot, a aplicação segue os princípios da Arquitetura Limpa (Clean Architecture) e do padrão CQRS (Command Query Responsibility Segregation), garantindo alta coesão, baixo acoplamento e manutenibilidade.

## 🛠️ Tecnologias Utilizadas
- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Hibernate**
- **PostgreSQL**
- **Redis**
- **Lombok**
- **JWT (JSON Web Token)**
- **Maven**

## 🚀 Funcionalidades Principais

### 1. Gerenciamento de Transações

- Registro de transações financeiras.
- Associação de transações a contas e usuários.
- Atualização automática do saldo após cada operação

### 2. Consultas de Saldos e Histórico

- Obtenção do saldo atual de uma conta.
- Uso de cache Redis para respostas rápidas e escaláveis.

### 3. Autenticação e Segurança

- Autenticação baseada em JWT.
- Controle de acesso por usuário autenticado.
- Integração de status entre sistemas de governança e UH (UhStatusIntegration).

### 4. Integração com Redis

- Armazenamento em cache de BalanceResponse e TransactionResponse.
- Redução de consultas repetitivas ao banco de dados.

## 🧱 Arquitetura

A aplicação segue os princípios da Clean Architecture, dividida em camadas independentes e testáveis:

- **Adapters**: Controladores REST, DTOs e Mappers.
- **Application**: Casos de uso (Commands e Queries) que contêm a lógica de orquestração.
- **Domain**: Entidades e regras de negócio puras.
- **Infrastructure**: Configurações, repositórios, cache e segurança.

  ## Padrão CQRS

## 🗃️ Banco de Dados

- **Commands**: Executam operações de escrita (ex: criar transação).
- **Queries**: Executam operações de leitura (ex: consultar saldo).

## ▶️ Como Executar

### 1. **Clone o repositório:**
   
  ```bash
    git clone https://github.com/marcondesjr96/santander-challenge.git
  ```

### 2. **Subir os containers do PostgreSQL e Redis:**
```bash
docker-compose up -d
```

### 3. **Executar a aplicação**:
```bash
./mvnw spring-boot:run
```
## A aplicação iniciará em:

http://localhost:8080


