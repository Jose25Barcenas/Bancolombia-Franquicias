package com.bancolombia.franquicias.application.service;

import com.bancolombia.franquicias.domain.model.Product;
import com.bancolombia.franquicias.domain.model.ProductStock;
import com.bancolombia.franquicias.domain.port.ProductPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductPort productPort;

    public Mono<Product> create(Product product) {
        return productPort.save(product);
    }

    public Mono<Product> findById(Long id) {
        return productPort.findById(id);
    }

    public Flux<Product> findByBranchId(Long branchId) {
        return productPort.findByBranchId(branchId);
    }

    public Mono<Product> update(Long id, Product product) {
        return productPort.update(id, product);
    }

    public Mono<Product> updateStock(Long id, Integer newStock) {
        return productPort.updateStock(id, newStock);
    }

    public Mono<Void> delete(Long id) {
        return productPort.delete(id);
    }

    public Flux<ProductStock> findProductsMaxStockByFranchise(Long franchiseId) {
        return productPort.findProductsMaxStockByFranchise(franchiseId);
    }
}
