package com.bancolombia.franquicias.application.service;

import com.bancolombia.franquicias.domain.model.Franchise;
import com.bancolombia.franquicias.domain.port.FranchisePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FranchiseService {
    private final FranchisePort franchisePort;

    public Mono<Franchise> create(Franchise franchise) {
        return franchisePort.save(franchise);
    }

    public Mono<Franchise> findById(Long id) {
        return franchisePort.findById(id);
    }

    public Flux<Franchise> findAll() {
        return franchisePort.findAll();
    }

    public Mono<Franchise> update(Long id, Franchise franchise) {
        return franchisePort.update(id, franchise);
    }

    public Mono<Void> delete(Long id) {
        return franchisePort.delete(id);
    }
}
