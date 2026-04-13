package com.bancolombia.franquicias.infrastructure.adapter.output.persistence;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface FranquiciaRepository extends ReactiveCrudRepository<FranquiciaEntity, Long> {
}
