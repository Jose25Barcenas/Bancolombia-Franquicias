# Franchises API

Reactive API for managing franchises, branches and products with hexagonal architecture.

## Features

- Spring Boot 3.2 with WebFlux (reactive)
- Hexagonal Architecture
- PostgreSQL with R2DBC
- Docker and Docker Compose
- Complete CRUD endpoints
- Input validation and error handling
- Logging and exception handling

## Requirements

- Java 17+
- Maven 3.9+
- Docker and Docker Compose (optional)
- PostgreSQL 15+ (if running locally)

## Local Installation

1. Clone repository
```bash
git clone <repository-url>
cd franquicias-api
```

2. Configure PostgreSQL
```bash
# PostgreSQL must be running on localhost:5432
# User: postgres
# Password: postgres
# Database: franchises
```

3. Build and run
```bash
mvn clean install
mvn spring-boot:run
```

Application available at http://localhost:8080

## Docker Installation

```bash
docker-compose up --build
```

Services:
- PostgreSQL on port 5432
- Application on port 8080

Stop services:
```bash
docker-compose down
```

## API Endpoints

### Franchises
- POST /api/franchises - Create
- GET /api/franchises - List all
- GET /api/franchises/{id} - Get by ID
- PUT /api/franchises/{id} - Update
- DELETE /api/franchises/{id} - Delete

### Branches
- POST /api/branches - Create
- GET /api/branches/{id} - Get by ID
- GET /api/branches/franchise/{franchiseId} - Get by franchise
- PUT /api/branches/{id} - Update
- DELETE /api/branches/{id} - Delete

### Products
- POST /api/products - Create
- GET /api/products/{id} - Get by ID
- GET /api/products/branch/{branchId} - Get by branch
- PUT /api/products/{id} - Update
- PATCH /api/products/{id}/stock?newStock=100 - Update stock
- DELETE /api/products/{id} - Delete
- GET /api/products/franchise/{franchiseId}/max-stock - Max stock by franchise

## Project Structure

```
src/main/java/com/bancolombia/franquicias/
├── domain/
│   ├── model/
│   └── port/
├── application/
│   ├── service/
│   ├── dto/
│   └── mapper/
└── infrastructure/
    ├── adapter/
    ├── config/
    └── exception/
```

## Technologies

- Spring Boot 3.2
- Spring WebFlux
- Spring Data R2DBC
- PostgreSQL
- Docker
- Maven
- Java 17
