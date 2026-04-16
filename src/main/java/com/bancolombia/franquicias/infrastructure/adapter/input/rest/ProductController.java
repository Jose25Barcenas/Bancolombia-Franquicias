package com.bancolombia.franquicias.infrastructure.adapter.input.rest;

import com.bancolombia.franquicias.application.dto.ProductDTO;
import com.bancolombia.franquicias.application.dto.ProductStockDTO;
import com.bancolombia.franquicias.application.mapper.ProductDTOMapper;
import com.bancolombia.franquicias.application.service.ProductService;
import com.bancolombia.franquicias.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductDTOMapper mapper;

    @PostMapping
    public Mono<ResponseEntity<ProductDTO>> create(@RequestBody ProductDTO dto) {
        log.info("Creating product: {}", dto.getName());
        return productService.create(mapper.toDomain(dto))
                .map(mapper::toDTO)
                .map(p -> ResponseEntity.status(HttpStatus.CREATED).body(p))
                .doOnError(e -> log.error("Error creating product", e));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDTO>> findById(@PathVariable Long id) {
        log.info("Finding product by id: {}", id);
        return productService.findById(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Product not found with id: " + id)));
    }

    @GetMapping("/branch/{branchId}")
    public Flux<ProductDTO> findByBranchId(@PathVariable Long branchId) {
        log.info("Finding products by branch id: {}", branchId);
        return productService.findByBranchId(branchId)
                .map(mapper::toDTO)
                .doOnError(e -> log.error("Error finding products", e));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductDTO>> update(@PathVariable Long id, @RequestBody ProductDTO dto) {
        log.info("Updating product with id: {}", id);
        return productService.update(id, mapper.toDomain(dto))
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Product not found with id: " + id)));
    }

    @PatchMapping("/{id}/stock")
    public Mono<ResponseEntity<ProductDTO>> updateStock(@PathVariable Long id, @RequestParam Integer newStock) {
        log.info("Updating stock for product id: {} to: {}", id, newStock);
        if (newStock < 0) {
            return Mono.error(new IllegalArgumentException("Stock cannot be negative"));
        }
        return productService.updateStock(id, newStock)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Product not found with id: " + id)))
                .doOnError(e -> log.error("Error updating stock", e));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        log.info("Deleting product with id: {}", id);
        return productService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .doOnError(e -> log.error("Error deleting product", e));
    }

    @GetMapping("/franchise/{franchiseId}/max-stock")
    public Flux<ProductStockDTO> findProductsMaxStockByFranchise(@PathVariable Long franchiseId) {
        log.info("Finding products with max stock by franchise id: {}", franchiseId);
        return productService.findProductsMaxStockByFranchise(franchiseId)
                .map(ps -> ProductStockDTO.builder()
                        .productId(ps.getProductId())
                        .productName(ps.getProductName())
                        .branchId(ps.getBranchId())
                        .branchName(ps.getBranchName())
                        .stock(ps.getStock())
                        .build())
                .doOnError(e -> log.error("Error finding max stock products", e));
    }
}
