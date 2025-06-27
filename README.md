# Esteira de Pedidos

Aplicacao simplificada para gerenciamento de instalacao de equipamentos. Possui um backend em Java/Spring Boot e frontend em React.

## Backend

- Java 17 e Spring Boot
- Armazena pedidos em banco H2 em memoria
- Endpoints REST em `/api/orders`
- Autenticacao via Keycloak (JWT) - configuracao basica

## Frontend

- React com Parcel
- Painel Kanban para visualizar pedidos
- Permite alterar o status via seletor

### Como executar

```bash
# Backend
cd backend
./mvnw spring-boot:run

# Frontend
cd ../frontend
npm install
npm start
```

Esses comandos iniciam a aplicacao em desenvolvimento.
