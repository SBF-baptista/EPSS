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

### Integracao Moskit

A aplicacao consulta a API V2 do Moskit para buscar informacoes dos produtos. O
endpoint base pode ser configurado em `backend/src/main/resources/application.properties`:

```
moskit.api.base-url=https://api.ms.prod.moskit.services/v2
moskit.api.key=<SUA_CHAVE>
```

Os produtos sao identificados pelo SKU informado no cadastro do pedido. A partir
do SKU e do nome retornado pelo Moskit, o sistema preenche marca, quantidade e
tipo do veiculo. Dados de rastreador e configuracao sao obtidos de uma tabela
local `ProductInfo`.
