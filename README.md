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

## ▶️ Como Executar o Projeto

### Pré-requisitos
- Docker e Docker Compose instalados
- Postman (para testar os endpoints)

### 1. **Clone o repositório:**
```bash
git clone https://github.com/marcondesjr96/santander-challenge.git
cd santander-challenge
```

### 2. **Subir toda a stack com Docker Compose:**
```bash
docker-compose up -d
```

**Este comando irá:**
- Criar e iniciar o container PostgreSQL na porta 5432
- Criar e iniciar o container Redis na porta 6379
- Compilar e iniciar a aplicação Spring Boot na porta 8080
- Configurar automaticamente a rede entre os containers

### 3. **Verificar se os serviços estão rodando:**
```bash
docker ps
```

**Você deve ver 3 containers ativos:**
- `transactions-api` (aplicação)
- `postgres-db` (banco de dados)
- `redis-db` (cache)

## 🧪 Testando os Endpoints

### Importar Collection no Postman
1. Abra o Postman
2. Clique em "Import"
3. Selecione o arquivo `SantanderChallenge.postman_collection.json`
4. A collection será importada com todos os endpoints configurados

### 🔐 Autenticação JWT

**IMPORTANTE:** Após o login, todas as requisições autenticadas utilizam o token JWT para identificar o usuário e sua conta associada. Não é necessário passar dados adicionais como ID do usuário ou conta - essas informações são extraídas automaticamente do token.

**Como funciona:**
1. O token JWT contém as informações do usuário (ID, login, etc.)
2. A aplicação decodifica o token e identifica automaticamente a conta associada
3. Todas as operações (depósito, saque, consulta) são realizadas na conta do usuário autenticado

### Fluxo de Teste Recomendado

#### 1. **Criar Usuário**
- **Endpoint:** `POST /api/v1/users/register`
- **Descrição:** Cria um novo usuário e automaticamente uma conta associada
- **Body exemplo:**
```json
{
    "fullName": "João Silva",
    "cpf": "123.456.789-00",
    "login": "joao.silva",
    "password": "minhasenha123"
}
```

#### 2. **Fazer Login**
- **Endpoint:** `POST /api/v1/auth/login`
- **Descrição:** Autentica o usuário e retorna um JWT token
- **Body exemplo:**
```json
{
    "login": "joao.silva",
    "password": "minhasenha123"
}
```
- **⚠️ IMPORTANTE:** O token JWT será automaticamente salvo na variável `{{token}}` do Postman

#### 3. **Depositar Dinheiro**
- **Endpoint:** `POST /api/v1/accounts/deposit`
- **Descrição:** Adiciona valor ao saldo da conta
- **Autenticação:** Bearer Token (automático via `{{token}}`)
- **⚠️ IMPORTANTE:** Os dados do usuário e conta são obtidos automaticamente do token JWT
- **Body exemplo:**
```json
{
    "amount": 500.00
}
```

#### 4. **Consultar Saldo**
- **Endpoint:** `GET /api/v1/accounts/balance`
- **Descrição:** Retorna saldo atual e histórico de transações (com cache Redis)
- **Autenticação:** Bearer Token (automático via `{{token}}`)
- **⚠️ IMPORTANTE:** Os dados do usuário e conta são obtidos automaticamente do token JWT

#### 5. **Pagar Conta**
- **Endpoint:** `POST /api/v1/accounts/pay`
- **Descrição:** Debita valor do saldo da conta
- **Autenticação:** Bearer Token (automático via `{{token}}`)
- **⚠️ IMPORTANTE:** Os dados do usuário e conta são obtidos automaticamente do token JWT
- **Body exemplo:**
```json
{
    "amount": 150.00
}
```

### 🔍 Monitoramento e Logs

#### Visualizar logs da aplicação:
```bash
docker logs -f transactions-api
```

#### Verificar dados no PostgreSQL:
```bash
docker exec -it postgres-db psql -U postgres -d santander_db
```

#### Verificar cache no Redis:
```bash
docker exec -it redis-db redis-cli
# Comandos úteis:
# KEYS * (listar todas as chaves)
# GET <chave> (obter valor de uma chave)
```

### 🛑 Parar os Serviços
```bash
docker-compose down
```

### 🔄 Rebuild (caso necessário)
```bash
docker-compose down
docker-compose up --build -d
```

## 🌐 URLs Importantes

- **Aplicação:** http://localhost:8080
- **PostgreSQL:** localhost:5432 (usuário: postgres, senha: postgres)
- **Redis:** localhost:6379

## 📋 Notas para Avaliação

1. **Arquitetura Limpa:** O projeto segue os princípios da Clean Architecture com separação clara de responsabilidades
2. **CQRS:** Implementação do padrão Command Query Responsibility Segregation
3. **Cache Redis:** Integração funcional com Redis para otimização de consultas
4. **Segurança JWT:** Autenticação stateless com tokens JWT
5. **Docker:** Containerização completa da aplicação e dependências
6. **Testes:** Collection Postman completa para validação dos endpoints

