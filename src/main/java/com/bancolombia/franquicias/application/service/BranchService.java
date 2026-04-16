package com.bancolombia.franquicias.application.service;

import com.bancolombia.franquicias.domain.model.Branch;
import com.bancolombia.franquicias.domain.port.BranchPort;
import com.bancolombia.franquicias.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class BranchService {
    private final BranchPort branchPort;

    public Mono<Branch> create(Branch branch) {
        log.debug("Creating branch: {}", branch.getName());
        if (branch.getName() == null || branch.getName().isBlank()) {
            return Mono.error(new IllegalArgumentException("Branch name cannot be empty"));
        }
        if (branch.getFranchiseId() == null || branch.getFranchiseId() <= 0) {
            return Mono.error(new IllegalArgumentException("Invalid franchise id"));
        }
        return branchPort.save(branch)
                .doOnSuccess(b -> log.info("Branch created with id: {}", b.getId()))
                .doOnError(e -> log.error("Error creating branch", e));
    }

    public Mono<Branch> findById(Long id) {
        log.debug("Finding branch by id: {}", id);
        if (id == null || id <= 0) {
            return Mono.error(new IllegalArgumentException("Invalid branch id"));
        }
        return branchPort.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Branch not found with id: " + id)))
                .doOnError(e -> log.error("Error finding branch", e));
    }

    public Flux<Branch> findByFranchiseId(Long franchiseId) {
        log.debug("Finding branches by franchise id: {}", franchiseId);
        if (franchiseId == null || franchiseId <= 0) {
            return Flux.error(new IllegalArgumentException("Invalid franchise id"));
        }
        return branchPort.findByFranchiseId(franchiseId)
                .doOnError(e -> log.error("Error finding branches", e));
    }

    public Mono<Branch> update(Long id, Branch branch) {
        log.debug("Updating branch with id: {}", id);
        if (id == null || id <= 0) {
            return Mono.error(new IllegalArgumentException("Invalid branch id"));
        }
        if (branch.getName() == null || branch.getName().isBlank()) {
            return Mono.error(new IllegalArgumentException("Branch name cannot be empty"));
        }
        return branchPort.update(id, branch)
                .doOnSuccess(b -> log.info("Branch updated with id: {}", b.getId()))
                .doOnError(e -> log.error("Error updating branch", e));
    }

    public Mono<Void> delete(Long id) {
        log.debug("Deleting branch with id: {}", id);
        if (id == null || id <= 0) {
            return Mono.error(new IllegalArgumentException("Invalid branch id"));
        }
        return branchPort.delete(id)
                .doOnSuccess(v -> log.info("Branch deleted with id: {}", id))
                .doOnError(e -> log.error("Error deleting branch", e));
    }
}
