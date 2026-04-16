#!/bin/bash

echo "=== Validación de Cambios - Franquicias API ==="
echo ""

# Colores
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Contador de validaciones
PASSED=0
FAILED=0

# Función para validar archivo
validate_file() {
    if [ -f "$1" ]; then
        echo -e "${GREEN}✓${NC} $1"
        ((PASSED++))
    else
        echo -e "${RED}✗${NC} $1"
        ((FAILED++))
    fi
}

# Función para validar que archivo NO existe
validate_not_exists() {
    if [ ! -f "$1" ]; then
        echo -e "${GREEN}✓${NC} $1 (eliminado)"
        ((PASSED++))
    else
        echo -e "${RED}✗${NC} $1 (aún existe)"
        ((FAILED++))
    fi
}

echo "1. Validando Modelos de Dominio (traducidos):"
validate_file "src/main/java/com/bancolombia/franquicias/domain/model/Franchise.java"
validate_file "src/main/java/com/bancolombia/franquicias/domain/model/Branch.java"
validate_file "src/main/java/com/bancolombia/franquicias/domain/model/Product.java"
validate_file "src/main/java/com/bancolombia/franquicias/domain/model/ProductStock.java"
echo ""

echo "2. Validando Puertos (traducidos):"
validate_file "src/main/java/com/bancolombia/franquicias/domain/port/FranchisePort.java"
validate_file "src/main/java/com/bancolombia/franquicias/domain/port/BranchPort.java"
validate_file "src/main/java/com/bancolombia/franquicias/domain/port/ProductPort.java"
echo ""

echo "3. Validando DTOs (traducidos):"
validate_file "src/main/java/com/bancolombia/franquicias/application/dto/FranchiseDTO.java"
validate_file "src/main/java/com/bancolombia/franquicias/application/dto/BranchDTO.java"
validate_file "src/main/java/com/bancolombia/franquicias/application/dto/ProductDTO.java"
validate_file "src/main/java/com/bancolombia/franquicias/application/dto/ProductStockDTO.java"
echo ""

echo "4. Validando Services (traducidos):"
validate_file "src/main/java/com/bancolombia/franquicias/application/service/FranchiseService.java"
validate_file "src/main/java/com/bancolombia/franquicias/application/service/BranchService.java"
validate_file "src/main/java/com/bancolombia/franquicias/application/service/ProductService.java"
echo ""

echo "5. Validando Controllers (traducidos):"
validate_file "src/main/java/com/bancolombia/franquicias/infrastructure/adapter/input/rest/FranchiseController.java"
validate_file "src/main/java/com/bancolombia/franquicias/infrastructure/adapter/input/rest/BranchController.java"
validate_file "src/main/java/com/bancolombia/franquicias/infrastructure/adapter/input/rest/ProductController.java"
echo ""

echo "6. Validando Entidades (traducidas):"
validate_file "src/main/java/com/bancolombia/franquicias/infrastructure/adapter/output/persistence/FranchiseEntity.java"
validate_file "src/main/java/com/bancolombia/franquicias/infrastructure/adapter/output/persistence/BranchEntity.java"
validate_file "src/main/java/com/bancolombia/franquicias/infrastructure/adapter/output/persistence/ProductEntity.java"
echo ""

echo "7. Validando Repositories (traducidos):"
validate_file "src/main/java/com/bancolombia/franquicias/infrastructure/adapter/output/persistence/FranchiseRepository.java"
validate_file "src/main/java/com/bancolombia/franquicias/infrastructure/adapter/output/persistence/BranchRepository.java"
validate_file "src/main/java/com/bancolombia/franquicias/infrastructure/adapter/output/persistence/ProductRepository.java"
echo ""

echo "8. Validando Adapters (traducidos):"
validate_file "src/main/java/com/bancolombia/franquicias/infrastructure/adapter/output/persistence/FranchiseAdapter.java"
validate_file "src/main/java/com/bancolombia/franquicias/infrastructure/adapter/output/persistence/BranchAdapter.java"
validate_file "src/main/java/com/bancolombia/franquicias/infrastructure/adapter/output/persistence/ProductAdapter.java"
echo ""

echo "9. Validando Mappers (SOLID - Separación de Responsabilidades):"
validate_file "src/main/java/com/bancolombia/franquicias/infrastructure/adapter/output/persistence/mapper/FranchiseMapper.java"
validate_file "src/main/java/com/bancolombia/franquicias/infrastructure/adapter/output/persistence/mapper/BranchMapper.java"
validate_file "src/main/java/com/bancolombia/franquicias/infrastructure/adapter/output/persistence/mapper/ProductMapper.java"
echo ""

echo "10. Validando Tests Unitarios:"
validate_file "src/test/java/com/bancolombia/franquicias/application/service/FranchiseServiceTest.java"
validate_file "src/test/java/com/bancolombia/franquicias/application/service/BranchServiceTest.java"
validate_file "src/test/java/com/bancolombia/franquicias/application/service/ProductServiceTest.java"
validate_file "src/test/java/com/bancolombia/franquicias/infrastructure/adapter/output/persistence/FranchiseAdapterTest.java"
echo ""

echo "11. Validando que archivos antiguos fueron eliminados:"
validate_not_exists "src/main/java/com/bancolombia/franquicias/domain/model/Franquicia.java"
validate_not_exists "src/main/java/com/bancolombia/franquicias/domain/model/Sucursal.java"
validate_not_exists "src/main/java/com/bancolombia/franquicias/domain/model/Producto.java"
validate_not_exists "src/main/java/com/bancolombia/franquicias/domain/model/ProductoStock.java"
echo ""

echo "12. Validando pom.xml con plugins de testing:"
if grep -q "maven-surefire-plugin" pom.xml && grep -q "jacoco-maven-plugin" pom.xml; then
    echo -e "${GREEN}✓${NC} pom.xml contiene plugins de testing"
    ((PASSED++))
else
    echo -e "${RED}✗${NC} pom.xml no contiene plugins de testing"
    ((FAILED++))
fi
echo ""

echo "=== Resumen ==="
echo -e "${GREEN}Validaciones Pasadas: $PASSED${NC}"
echo -e "${RED}Validaciones Fallidas: $FAILED${NC}"
echo ""

if [ $FAILED -eq 0 ]; then
    echo -e "${GREEN}✓ Todos los cambios se aplicaron correctamente${NC}"
    exit 0
else
    echo -e "${RED}✗ Hay cambios que no se aplicaron correctamente${NC}"
    exit 1
fi
