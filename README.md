# Franquicias API

API reactiva para gestionar franquicias, sucursales y productos con arquitectura hexagonal.

## Características

- ✅ Spring Boot 3.2 con WebFlux (reactivo)
- ✅ Arquitectura Hexagonal
- ✅ PostgreSQL con R2DBC
- ✅ Docker y Docker Compose
- ✅ Endpoints CRUD completos
- ✅ Endpoint para obtener producto con máximo stock por sucursal

## Requisitos

- Java 17+
- Maven 3.9+
- Docker y Docker Compose (opcional)
- PostgreSQL 15+ (si ejecutas localmente)

## Instalación Local

### 1. Clonar el repositorio

```bash
git clone <repository-url>
cd franquicias-api
```

### 2. Configurar PostgreSQL

Asegúrate de que PostgreSQL esté corriendo en `localhost:5432` con:
- Usuario: `postgres`
- Contraseña: `postgres`
- Base de datos: `franquicias`

O ejecuta el script `init.sql` manualmente.

### 3. Compilar y ejecutar

```bash
mvn clean install
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## Instalación con Docker

### 1. Construir y ejecutar con Docker Compose

```bash
docker-compose up --build
```

Esto levantará:
- PostgreSQL en puerto 5432
- Aplicación en puerto 8080

### 2. Detener los servicios

```bash
docker-compose down
```

## Endpoints

### Franquicias

- `POST /api/franquicias` - Crear franquicia
- `GET /api/franquicias` - Obtener todas
- `GET /api/franquicias/{id}` - Obtener por ID
- `PUT /api/franquicias/{id}` - Actualizar nombre
- `DELETE /api/franquicias/{id}` - Eliminar

### Sucursales

- `POST /api/sucursales` - Crear sucursal
- `GET /api/sucursales/{id}` - Obtener por ID
- `GET /api/sucursales/franquicia/{franquiciaId}` - Obtener por franquicia
- `PUT /api/sucursales/{id}` - Actualizar nombre
- `DELETE /api/sucursales/{id}` - Eliminar

### Productos

- `POST /api/productos` - Crear producto
- `GET /api/productos/{id}` - Obtener por ID
- `GET /api/productos/sucursal/{sucursalId}` - Obtener por sucursal
- `PUT /api/productos/{id}` - Actualizar nombre
- `PATCH /api/productos/{id}/stock?nuevoStock=100` - Actualizar stock
- `DELETE /api/productos/{id}` - Eliminar
- `GET /api/productos/franquicia/{franquiciaId}/max-stock` - Productos con máximo stock por franquicia

## Ejemplos de Uso

### Crear Franquicia

```bash
curl -X POST http://localhost:8080/api/franquicias \
  -H "Content-Type: application/json" \
  -d '{"nombre": "Franquicia A"}'
```

### Crear Sucursal

```bash
curl -X POST http://localhost:8080/api/sucursales \
  -H "Content-Type: application/json" \
  -d '{"franquiciaId": 1, "nombre": "Sucursal Centro"}'
```

### Crear Producto

```bash
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -d '{"sucursalId": 1, "nombre": "Producto X", "stock": 50}'
```

### Actualizar Stock

```bash
curl -X PATCH http://localhost:8080/api/productos/1/stock?nuevoStock=100
```

### Obtener Productos con Máximo Stock

```bash
curl http://localhost:8080/api/productos/franquicia/1/max-stock
```

## Estructura del Proyecto

```
franquicias-api/
├── src/main/java/com/bancolombia/franquicias/
│   ├── domain/
│   │   ├── model/          # Modelos de dominio
│   │   └── port/           # Puertos (interfaces)
│   ├── application/
│   │   ├── service/        # Servicios de aplicación
│   │   └── dto/            # Data Transfer Objects
│   └── infrastructure/
│       └── adapter/
│           ├── input/rest/ # Controladores REST
│           └── output/     # Adaptadores de persistencia
├── pom.xml
├── Dockerfile
├── docker-compose.yml
├── init.sql
└── README.md
```

## Commits Convencionales

El proyecto utiliza commits convencionales:

- `feat:` Nueva funcionalidad
- `fix:` Corrección de errores
- `docs:` Cambios en documentación
- `style:` Cambios de formato
- `refactor:` Refactorización de código
- `test:` Adición de tests
- `chore:` Cambios en configuración

## Tecnologías

- Spring Boot 3.2
- Spring WebFlux
- Spring Data R2DBC
- PostgreSQL
- Docker
- Maven

## Licencia

MIT
