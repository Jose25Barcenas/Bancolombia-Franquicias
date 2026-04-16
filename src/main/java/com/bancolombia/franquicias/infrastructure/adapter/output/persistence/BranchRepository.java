package com.bancolombia.franquicias.infrastructure.adapter.output.persistence;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BranchRepository extends ReactiveCrudRepository<BranchEntity, Long> {
    @Query("SELECT * FROM branches WHERE franchise_id = :franchiseId")
    Flux<BranchEntity> findByFranchiseId(Long franchiseId);
}
