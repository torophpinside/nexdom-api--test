# Controle de Estoque

Aplicação RESTful de controle de estoque com backend em Java Spring Boot e frontend em Vue.js.

## Tecnologias

### Backend
- Java 21
- Spring Boot 3.3.5 (Web, Data JPA, Validation)
- H2 (banco em memória)
- Maven

### Frontend
- Vue 3
- Vite
- Axios

## Estrutura
```
backend/   <- aplicação Java Spring Boot
frontend/  <- aplicação Vue.js
```

## Como rodar o Backend

Requisitos: Java 21 e Maven.

```bash
cd backend
./mvnw spring-boot:run
```

Ou com Maven instalado:

```bash
cd backend
mvn spring-boot:run
```

A API sobe em: `http://localhost:8081`

Endpoints principais:
- `GET /produtos` — listar produtos
- `GET /produtos/{codigo}` — buscar produto por código
- `POST /produtos` — criar produto
- `PUT /produtos/{codigo}` — atualizar produto
- `DELETE /produtos/{codigo}` — excluir produto
- `GET /produtos/tipo/{tipo}` — consulta por tipo (quantidade disponível e saída)
- `GET /produtos/{codigo}/lucro` — lucro por produto
- `POST /movimentos` — registrar entrada ou saída de estoque
- `GET /movimentos` — listar movimentos

Console H2: `http://localhost:8081/h2-console` (JDBC URL: `jdbc:h2:mem:estoque`)

### Executar testes

```bash
cd backend
./mvnw test
```

## Como rodar o Frontend

Requisitos: Node.js e npm.

```bash
cd frontend
npm install
npm run dev
```

A aplicação sobe em: `http://localhost:5173`

O frontend chama a API diretamente em `http://localhost:8081` (sem proxy).

### Build de produção

```bash
cd frontend
npm run build
```

## Fluxo rápido

1. Inicie o backend: `cd backend && ./mvnw spring-boot:run`
2. Inicie o frontend: `cd frontend && npm install && npm run dev`
3. Acesse `http://localhost:5173`
