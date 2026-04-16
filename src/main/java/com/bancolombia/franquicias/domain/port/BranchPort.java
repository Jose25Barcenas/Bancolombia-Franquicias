package com.bancolombia.franquicias.domain.port;

import com.bancolombia.franquicias.domain.model.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchPort {
    Mono<Branch> save(Branch branch);
    Mono<Branch> findById(Long id);
    Flux<Branch> findByFranchiseId(Long franchiseId);
    Mono<Branch> update(Long id, Branch branch);
    Mono<Void> delete(Long id);
}
