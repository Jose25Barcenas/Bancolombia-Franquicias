# Cambios Realizados - Franquicias API

## 1. Traducción al Inglés ✅

### Modelos de Dominio
- `Franquicia.java` → `Franchise.java`
- `Sucursal.java` → `Branch.java`
- `Producto.java` → `Product.java`
- `ProductoStock.java` → `ProductStock.java`

### Puertos (Interfaces)
- `FranquiciaPort.java` → `FranchisePort.java`
- `SucursalPort.java` → `BranchPort.java`
- `ProductoPort.java` → `ProductPort.java`

### DTOs
- `FranquiciaDTO.java` → `FranchiseDTO.java`
- `SucursalDTO.java` → `BranchDTO.java`
- `ProductoDTO.java` → `ProductDTO.java`
- `ProductoStockDTO.java` → `ProductStockDTO.java`

### Services
- `FranquiciaService.java` → `FranchiseService.java`
- `SucursalService.java` → `BranchService.java`
- `ProductoService.java` → `ProductService.java`

### Controllers
- `FranquiciaController.java` → `FranchiseController.java` (endpoint: `/api/franchises`)
- `SucursalController.java` → `BranchController.java` (endpoint: `/api/branches`)
- `ProductoController.java` → `ProductController.java` (endpoint: `/api/products`)

### Entidades de Persistencia
- `FranquiciaEntity.java` → `FranchiseEntity.java` (tabla: `franchises`)
- `SucursalEntity.java` → `BranchEntity.java` (tabla: `branches`)
- `ProductoEntity.java` → `ProductEntity.java` (tabla: `products`)

### Repositories
- `FranquiciaRepository.java` → `FranchiseRepository.java`
- `SucursalRepository.java` → `BranchRepository.java`
- `ProductoRepository.java` → `ProductRepository.java`

### Adapters
- `FranquiciaAdapter.java` → `FranchiseAdapter.java`
- `SucursalAdapter.java` → `BranchAdapter.java`
- `ProductoAdapter.java` → `ProductAdapter.java`

## 2. Refactorización SOLID ✅

### Separación de Responsabilidades - Mappers
Se creó una nueva capa de mappers para separar la lógica de transformación de entidades a modelos de dominio:

- `FranchiseMapper.java` - Mapea entre `FranchiseEntity` y `Franchise`
- `BranchMapper.java` - Mapea entre `BranchEntity` y `Branch`
- `ProductMapper.java` - Mapea entre `ProductEntity` y `Product`

**Beneficios:**
- Los adapters ahora solo orquestan la lógica de persistencia
- Los mappers tienen una única responsabilidad: transformación de datos
- Código más testeable y mantenible

## 3. Tests Unitarios ✅

### Tests de Services
- `FranchiseServiceTest.java` - Tests para crear, buscar, actualizar y eliminar franquicias
- `BranchServiceTest.java` - Tests para operaciones de sucursales
- `ProductServiceTest.java` - Tests para operaciones de productos

### Tests de Adapters
- `FranchiseAdapterTest.java` - Tests para la capa de persistencia

**Cobertura:**
- Métodos CRUD básicos
- Operaciones de búsqueda
- Manejo de errores

## 4. Mejoras en Build (pom.xml) ✅

### Plugins Agregados
- **Maven Surefire Plugin** - Para ejecutar tests unitarios
- **JaCoCo Maven Plugin** - Para generar reportes de cobertura de código

### Configuración
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.0.0</version>
</plugin>
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.10</version>
</plugin>
```

## 5. Estructura del Proyecto

```
src/
├── main/java/com/bancolombia/franquicias/
│   ├── application/
│   │   ├── dto/
│   │   │   ├── FranchiseDTO.java
│   │   │   ├── BranchDTO.java
│   │   │   ├── ProductDTO.java
│   │   │   └── ProductStockDTO.java
│   │   └── service/
│   │       ├── FranchiseService.java
│   │       ├── BranchService.java
│   │       └── ProductService.java
│   ├── domain/
│   │   ├── model/
│   │   │   ├── Franchise.java
│   │   │   ├── Branch.java
│   │   │   ├── Product.java
│   │   │   └── ProductStock.java
│   │   └── port/
│   │       ├── FranchisePort.java
│   │       ├── BranchPort.java
│   │       └── ProductPort.java
│   └── infrastructure/
│       └── adapter/
│           ├── input/rest/
│           │   ├── FranchiseController.java
│           │   ├── BranchController.java
│           │   └── ProductController.java
│           └── output/persistence/
│               ├── mapper/
│               │   ├── FranchiseMapper.java
│               │   ├── BranchMapper.java
│               │   └── ProductMapper.java
│               ├── FranchiseAdapter.java
│               ├── BranchAdapter.java
│               ├── ProductAdapter.java
│               ├── FranchiseRepository.java
│               ├── BranchRepository.java
│               ├── ProductRepository.java
│               ├── FranchiseEntity.java
│               ├── BranchEntity.java
│               └── ProductEntity.java
└── test/java/com/bancolombia/franquicias/
    ├── application/service/
    │   ├── FranchiseServiceTest.java
    │   ├── BranchServiceTest.java
    │   └── ProductServiceTest.java
    └── infrastructure/adapter/output/persistence/
        └── FranchiseAdapterTest.java
```

## 6. Próximos Pasos

Para compilar y ejecutar tests:
```bash
mvn clean test
```

Para generar reporte de cobertura:
```bash
mvn clean test jacoco:report
```

El reporte estará disponible en: `target/site/jacoco/index.html`

## 7. Notas Importantes

- Todos los nombres de métodos han sido traducidos al inglés
- Las tablas de base de datos han sido renombradas (franquicias → franchises, etc.)
- Los endpoints REST han sido actualizados
- La arquitectura hexagonal se mantiene intacta
- Se agregaron mappers para mejorar la separación de responsabilidades (SOLID)
- Se incluyen tests unitarios básicos para validar la funcionalidad
