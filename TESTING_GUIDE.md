# Guía de Testing - Franquicias API

## Requisitos Previos

- Java 17 o superior
- Maven 3.8.0 o superior

## Ejecutar Tests

### 1. Ejecutar todos los tests
```bash
mvn clean test
```

### 2. Ejecutar tests de un módulo específico
```bash
# Tests de services
mvn test -Dtest=*ServiceTest

# Tests de adapters
mvn test -Dtest=*AdapterTest
```

### 3. Ejecutar un test específico
```bash
mvn test -Dtest=FranchiseServiceTest
```

## Generar Reporte de Cobertura

### 1. Generar reporte con JaCoCo
```bash
mvn clean test jacoco:report
```

### 2. Ver el reporte
El reporte se genera en: `target/site/jacoco/index.html`

Abre el archivo en tu navegador para ver:
- Cobertura general del proyecto
- Cobertura por clase
- Líneas cubiertas vs no cubiertas

## Estructura de Tests

### Tests de Services
Ubicación: `src/test/java/com/bancolombia/franquicias/application/service/`

- **FranchiseServiceTest.java**
  - `testCreate()` - Valida creación de franquicias
  - `testFindById()` - Valida búsqueda por ID
  - `testFindAll()` - Valida obtención de todas las franquicias
  - `testUpdate()` - Valida actualización
  - `testDelete()` - Valida eliminación

- **BranchServiceTest.java**
  - Tests similares para sucursales

- **ProductServiceTest.java**
  - Tests similares para productos
  - `testFindProductsMaxStockByFranchise()` - Valida búsqueda de productos con máximo stock

### Tests de Adapters
Ubicación: `src/test/java/com/bancolombia/franquicias/infrastructure/adapter/output/persistence/`

- **FranchiseAdapterTest.java**
  - `testSave()` - Valida persistencia
  - `testFindById()` - Valida lectura
  - `testFindAll()` - Valida listado
  - `testDelete()` - Valida eliminación

## Tecnologías de Testing

- **JUnit 5** - Framework de testing
- **Mockito** - Mocking de dependencias
- **Reactor Test** - Testing de streams reactivos
- **StepVerifier** - Validación de flujos Mono/Flux

## Ejemplo de Test

```java
@Test
void testCreate() {
    Franchise franchise = Franchise.builder().name("Test Franchise").build();
    Franchise saved = Franchise.builder().id(1L).name("Test Franchise").build();

    when(franchisePort.save(any(Franchise.class))).thenReturn(Mono.just(saved));

    StepVerifier.create(franchiseService.create(franchise))
            .expectNext(saved)
            .verifyComplete();
}
```

## Mejores Prácticas

1. **Usar Mocks** - Mockear dependencias externas
2. **Usar StepVerifier** - Para validar flujos reactivos
3. **Nombres descriptivos** - Los nombres de tests deben describir qué se prueba
4. **Arrange-Act-Assert** - Estructura clara de tests
5. **Una asserción por test** - Mantener tests simples y enfocados

## Troubleshooting

### Error: "No tests found"
```bash
# Asegúrate de que los tests están en src/test/java
# y que los nombres terminan en Test.java
```

### Error: "JAVA_HOME not set"
```bash
# En Linux/Mac
export JAVA_HOME=/path/to/java

# En Windows
set JAVA_HOME=C:\path\to\java
```

### Error: "Maven not found"
```bash
# Instala Maven o agrega su bin al PATH
# Descarga desde: https://maven.apache.org/download.cgi
```

## Próximos Pasos

1. Agregar más tests de integración
2. Aumentar cobertura a 80%+
3. Agregar tests de controllers
4. Agregar tests de validación de DTOs
5. Agregar tests de error handling

## Recursos

- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Project Reactor Testing](https://projectreactor.io/docs/core/release/reference/#testing)
- [JaCoCo Maven Plugin](https://www.jacoco.org/jacoco/trunk/doc/maven.html)
