package com.bancolombia.franquicias.infrastructure.adapter.output.persistence;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductoRepository extends ReactiveCrudRepository<ProductoEntity, Long> {
    @Query("SELECT * FROM productos WHERE sucursal_id = :sucursalId")
    Flux<ProductoEntity> findBySucursalId(Long sucursalId);

    @Query("SELECT * FROM productos WHERE sucursal_id = :sucursalId ORDER BY stock DESC LIMIT 1")
    Mono<ProductoEntity> findMaxStockBySucursalId(Long sucursalId);
}
