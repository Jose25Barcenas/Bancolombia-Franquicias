package com.bancolombia.franquicias.application.service;

import com.bancolombia.franquicias.domain.model.Franchise;
import com.bancolombia.franquicias.domain.port.FranchisePort;
import com.bancolombia.franquicias.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class FranchiseService {
    private final FranchisePort franchisePort;

    public Mono<Franchise> create(Franchise franchise) {
        log.debug("Creating franchise: {}", franchise.getName());
        if (franchise.getName() == null || franchise.getName().isBlank()) {
            return Mono.error(new IllegalArgumentException("Franchise name cannot be empty"));
        }
        return franchisePort.save(franchise)
                .doOnSuccess(f -> log.info("Franchise created with id: {}", f.getId()))
                .doOnError(e -> log.error("Error creating franchise", e));
    }

    public Mono<Franchise> findById(Long id) {
        log.debug("Finding franchise by id: {}", id);
        if (id == null || id <= 0) {
            return Mono.error(new IllegalArgumentException("Invalid franchise id"));
        }
        return franchisePort.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Franchise not found with id: " + id)))
                .doOnError(e -> log.error("Error finding franchise", e));
    }

    public Flux<Franchise> findAll() {
        log.debug("Finding all franchises");
        return franchisePort.findAll()
                .doOnError(e -> log.error("Error finding franchises", e));
    }

    public Mono<Franchise> update(Long id, Franchise franchise) {
        log.debug("Updating franchise with id: {}", id);
        if (id == null || id <= 0) {
            return Mono.error(new IllegalArgumentException("Invalid franchise id"));
        }
        if (franchise.getName() == null || franchise.getName().isBlank()) {
            return Mono.error(new IllegalArgumentException("Franchise name cannot be empty"));
        }
        return franchisePort.update(id, franchise)
                .doOnSuccess(f -> log.info("Franchise updated with id: {}", f.getId()))
                .doOnError(e -> log.error("Error updating franchise", e));
    }

    public Mono<Void> delete(Long id) {
        log.debug("Deleting franchise with id: {}", id);
        if (id == null || id <= 0) {
            return Mono.error(new IllegalArgumentException("Invalid franchise id"));
        }
        return franchisePort.delete(id)
                .doOnSuccess(v -> log.info("Franchise deleted with id: {}", id))
                .doOnError(e -> log.error("Error deleting franchise", e));
    }
}
