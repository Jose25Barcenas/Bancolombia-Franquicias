package com.bancolombia.franquicias.infrastructure.adapter.output.persistence;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, Long> {
    @Query("SELECT * FROM products WHERE branch_id = :branchId")
    Flux<ProductEntity> findByBranchId(Long branchId);

    @Query("SELECT * FROM products WHERE branch_id = :branchId ORDER BY stock DESC LIMIT 1")
    Mono<ProductEntity> findMaxStockByBranchId(Long branchId);
}
