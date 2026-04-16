package com.bancolombia.franquicias.infrastructure.adapter.output.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FranchiseRepository extends ReactiveCrudRepository<FranchiseEntity, Long> {
}
