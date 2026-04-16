package com.bancolombia.franquicias.domain.port;

import com.bancolombia.franquicias.domain.model.Product;
import com.bancolombia.franquicias.domain.model.ProductStock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductPort {
    Mono<Product> save(Product product);
    Mono<Product> findById(Long id);
    Flux<Product> findByBranchId(Long branchId);
    Mono<Product> update(Long id, Product product);
    Mono<Product> updateStock(Long id, Integer newStock);
    Mono<Void> delete(Long id);
    Flux<ProductStock> findProductsMaxStockByFranchise(Long franchiseId);
}
