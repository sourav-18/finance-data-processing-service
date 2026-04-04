# Finance Dashboard Backend

A backend system for managing financial records with role-based access control, built with Spring Boot, PostgreSQL, and Redis.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot |
| Database | PostgreSQL |
| Cache / Rate Limiting | Redis |
| Auth | JWT |
| API Docs | Swagger (SpringDoc OpenAPI) |

---

## Features

- **JWT Authentication** — Secure login with token-based auth
- **Role Based Authorization** — Three roles: Admin, Analyst, Viewer with different access levels
- **Financial Records CRUD** — Create, read, update, delete financial entries
- **Dashboard Summary APIs** — Total income, expenses, net balance, category breakdown, weekly trends
- **Dynamic Filtering** — Filter records by date, category, type on every Finance API
- **Search and Pagination** — All list APIs support search and pagination
- **Rate Limiting** — Token bucket algorithm via Redis applied globally on all routes
- **Soft Delete** — Records are never physically removed. Deletion sets `isDeleted = true` and all queries automatically filter them out
- **Instant Role and Status Propagation** — When an admin updates a user's role or deactivates a user, the change reflects immediately in Login User.

---

## Role Permissions

| Action | Viewer | Analyst | Admin |
|---|---|---|---|
| View records | ✅ | ✅ | ✅ |
| View dashboard summary | ❌ | ✅ | ✅ |
| Create / Update / Delete records | ❌ | ❌ | ✅ |
| Manage users | ❌ | ❌ | ✅ |

---

## Run Locally with Docker

Make sure Docker and Docker Compose are installed.

```bash
docker compose up
```

This will start the Spring Boot app, PostgreSQL, and Redis together.

| Service | URL |
|---|---|
| Health Check | http://localhost:4050/api/v1/health |
| Swagger UI | http://localhost:4050/swagger-ui/index.html |

---

## Default Login Credentials

Three seeded users are available out of the box for testing:

```json
{ "email": "admin@gmail.com",   "password": "admin"   }
{ "email": "analyst@gmail.com", "password": "analyst" }
{ "email": "viewer@gmail.com",  "password": "viewer"  }
```

---

## API Documentation

**Swagger UI (Local)**
```
http://localhost:4050/swagger-ui/index.html
```

**Postman Collection**
```
https://www.postman.com/cloudy-crescent-770366/workspace/public-api/collection/18039051-912ada7d-915e-491b-bbbd-eaa79f0c55d3
```

---

## Live Demo (Deployed)

| Service | URL |
|---|---|
| Health Check | http://34.180.21.37/api/v1/health |
| Swagger UI | http://34.180.21.37/swagger-ui/index.html |

---

## Assumptions Made

- Financial records belong to the organization (not per user). All roles interact with the same shared pool of records.
- Net balance can go negative. There is no wallet system. Net balance is purely computed as `Total Income - Total Expense`.
- `createdBy` is stored on each record for audit purposes, to track which user entered a transaction.
- Role and status changes made by an admin take effect immediately on the affected user's next request. 
- Soft delete is implemented using an `isDeleted` boolean flag on each record. Deleted records are never physically removed from the database, they are simply filtered out from all queries.

---

## Tradeoffs and Known Limitations

| Area | Decision / Limitation |
|---|---|
| Weekly trend calculation | Weeks are calculated as fixed 7-day chunks starting from day 1 of the month (1–7, 8–14, etc.) rather than real calendar weeks (Mon–Sun). This is a known simplification. |
| Rate limiting scope | Rate limiting is applied globally on every route. It is not configured per route or per role. |
| Error messages | Error responses are functional but could be more descriptive and consistent in some edge cases. |

---

## Running Without Docker

1. Make sure PostgreSQL and Redis are running locally
2. Create a database named `finances` in PostgreSQL:

```sql
CREATE DATABASE finances;
```

3. Update `src/main/resources/application.properties` with your credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/finances
spring.datasource.username=your_pg_username
spring.datasource.password=your_pg_password

spring.redis.host=localhost
spring.redis.port=6379
```

4. Run the app:

```bash
./mvnw spring-boot:run
```

---

## Sample Data

The app is pre-seeded with sample data on startup so you can explore the APIs immediately without manually creating records. This includes sample users with all three roles and a set of financial records across different categories, types, and dates.
