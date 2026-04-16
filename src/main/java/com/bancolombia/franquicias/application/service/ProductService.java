package com.bancolombia.franquicias.application.service;

import com.bancolombia.franquicias.domain.model.Product;
import com.bancolombia.franquicias.domain.model.ProductStock;
import com.bancolombia.franquicias.domain.port.ProductPort;
import com.bancolombia.franquicias.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductPort productPort;

    public Mono<Product> create(Product product) {
        log.debug("Creating product: {}", product.getName());
        if (product.getName() == null || product.getName().isBlank()) {
            return Mono.error(new IllegalArgumentException("Product name cannot be empty"));
        }
        if (product.getBranchId() == null || product.getBranchId() <= 0) {
            return Mono.error(new IllegalArgumentException("Invalid branch id"));
        }
        if (product.getStock() == null || product.getStock() < 0) {
            return Mono.error(new IllegalArgumentException("Stock cannot be negative"));
        }
        return productPort.save(product)
                .doOnSuccess(p -> log.info("Product created with id: {}", p.getId()))
                .doOnError(e -> log.error("Error creating product", e));
    }

    public Mono<Product> findById(Long id) {
        log.debug("Finding product by id: {}", id);
        if (id == null || id <= 0) {
            return Mono.error(new IllegalArgumentException("Invalid product id"));
        }
        return productPort.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Product not found with id: " + id)))
                .doOnError(e -> log.error("Error finding product", e));
    }

    public Flux<Product> findByBranchId(Long branchId) {
        log.debug("Finding products by branch id: {}", branchId);
        if (branchId == null || branchId <= 0) {
            return Flux.error(new IllegalArgumentException("Invalid branch id"));
        }
        return productPort.findByBranchId(branchId)
                .doOnError(e -> log.error("Error finding products", e));
    }

    public Mono<Product> update(Long id, Product product) {
        log.debug("Updating product with id: {}", id);
        if (id == null || id <= 0) {
            return Mono.error(new IllegalArgumentException("Invalid product id"));
        }
        if (product.getName() == null || product.getName().isBlank()) {
            return Mono.error(new IllegalArgumentException("Product name cannot be empty"));
        }
        return productPort.update(id, product)
                .doOnSuccess(p -> log.info("Product updated with id: {}", p.getId()))
                .doOnError(e -> log.error("Error updating product", e));
    }

    public Mono<Product> updateStock(Long id, Integer newStock) {
        log.debug("Updating stock for product id: {} to: {}", id, newStock);
        if (id == null || id <= 0) {
            return Mono.error(new IllegalArgumentException("Invalid product id"));
        }
        if (newStock == null || newStock < 0) {
            return Mono.error(new IllegalArgumentException("Stock cannot be negative"));
        }
        return productPort.updateStock(id, newStock)
                .doOnSuccess(p -> log.info("Product stock updated with id: {}", p.getId()))
                .doOnError(e -> log.error("Error updating stock", e));
    }

    public Mono<Void> delete(Long id) {
        log.debug("Deleting product with id: {}", id);
        if (id == null || id <= 0) {
            return Mono.error(new IllegalArgumentException("Invalid product id"));
        }
        return productPort.delete(id)
                .doOnSuccess(v -> log.info("Product deleted with id: {}", id))
                .doOnError(e -> log.error("Error deleting product", e));
    }

    public Flux<ProductStock> findProductsMaxStockByFranchise(Long franchiseId) {
        log.debug("Finding products with max stock by franchise id: {}", franchiseId);
        if (franchiseId == null || franchiseId <= 0) {
            return Flux.error(new IllegalArgumentException("Invalid franchise id"));
        }
        return productPort.findProductsMaxStockByFranchise(franchiseId)
                .doOnError(e -> log.error("Error finding max stock products", e));
    }
}
