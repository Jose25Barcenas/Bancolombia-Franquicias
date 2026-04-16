package com.bancolombia.franquicias.domain.port;

import com.bancolombia.franquicias.domain.model.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchisePort {
    Mono<Franchise> save(Franchise franchise);
    Mono<Franchise> findById(Long id);
    Flux<Franchise> findAll();
    Mono<Franchise> update(Long id, Franchise franchise);
    Mono<Void> delete(Long id);
}
