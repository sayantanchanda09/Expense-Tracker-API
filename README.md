# Expense Tracker API

A secure, full-featured REST API for tracking personal expenses, built with Spring Boot. Built as a hands-on project to relearn and deepen Java backend development skills, covering the full stack of a modern production-style API: layered architecture, JWT authentication, per-user data ownership, validation, and centralized error handling.

## Features

- **User authentication** — registration and login secured with BCrypt password hashing and JWT (JSON Web Tokens)
- **Per-user data ownership** — each user can only access, edit, or delete their own expenses
- **Full CRUD** — create, read, update, and delete expense records
- **Pagination, filtering, and sorting** — query expenses by category, page, size, and sort order
- **Input validation** — rejects invalid data (empty titles, negative amounts) with clean error messages
- **Centralized error handling** — consistent, safe JSON error responses (no leaked stack traces)
- **Environment-based configuration** — separate profiles for local development (H2) and production (PostgreSQL)
- **Dockerized** — includes a multi-stage Dockerfile for containerized deployment

## Tech Stack

- **Java 26**
- **Spring Boot 4.1** (Spring Web, Spring Data JPA, Spring Security)
- **JWT** via the JJWT library
- **H2** (local development database)
- **PostgreSQL** (production database, hosted on Neon)
- **Maven** (build tool)
- **Docker** (containerization)

## Architecture

The project follows a standard layered architecture:

Controller → handles HTTP requests/responses
Service → business logic, validation, ownership checks
Repository → database access (Spring Data JPA)
Entity → maps Java classes to database tables


## API Endpoints

### Auth
| Method | Endpoint | Description | Auth required |
|---|---|---|---|
| POST | `/api/auth/register` | Register a new user | No |
| POST | `/api/auth/login` | Log in and receive a JWT | No |

### Expenses
| Method | Endpoint | Description | Auth required |
|---|---|---|---|
| POST | `/api/expenses` | Create a new expense | Yes |
| GET | `/api/expenses` | Get paginated list of your expenses (supports `?category=`, `?page=`, `?size=`, `?sortBy=`) | Yes |
| GET | `/api/expenses/{id}` | Get a specific expense | Yes |
| PUT | `/api/expenses/{id}` | Update a specific expense | Yes |
| DELETE | `/api/expenses/{id}` | Delete a specific expense | Yes |

All expense endpoints require a valid JWT in the `Authorization: Bearer <token>` header, obtained from `/api/auth/login`.

## Running Locally

**Prerequisites:** Java 26, Maven

```bash
git clone https://github.com/sayantanchanda09/Expense-Tracker-API.git
cd Expense-Tracker-API
./mvnw spring-boot:run
```

The app runs on `http://localhost:8080` using an in-memory H2 database by default (`local` profile). The H2 console is available at `http://localhost:8080/h2-console`.

**Note:** the H2 database resets on every restart — this is expected behavior for local development.

## Example Usage

**Register:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username": "alice", "password": "password123"}'
```

**Login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "alice", "password": "password123"}'
```

**Create an expense (using the token from login):**
```bash
curl -X POST http://localhost:8080/api/expenses \
  -H "Authorization: Bearer <your-token-here>" \
  -H "Content-Type: application/json" \
  -d '{"title": "Groceries", "amount": 45.50, "category": "Food"}'
```

## Production Deployment

The app is Docker-ready and configured for deployment with a `prod` Spring profile, expecting the following environment variables:

| Variable | Purpose |
|---|---|
| `SPRING_PROFILES_ACTIVE` | Set to `prod` |
| `DATABASE_URL` | JDBC URL for the PostgreSQL database |
| `DATABASE_USERNAME` | Database username |
| `DATABASE_PASSWORD` | Database password |
| `JWT_SECRET` | Secret key used to sign JWTs |

## What's Next

- Automated unit and integration tests
- Live deployment
- DTOs for request bodies (currently reusing entities directly for input)

---
Built by [Sayantan Chanda](https://github.com/sayantanchanda09)