package com.bancolombia.franquicias.application.service;

import com.bancolombia.franquicias.domain.model.Branch;
import com.bancolombia.franquicias.domain.port.BranchPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BranchService {
    private final BranchPort branchPort;

    public Mono<Branch> create(Branch branch) {
        return branchPort.save(branch);
    }

    public Mono<Branch> findById(Long id) {
        return branchPort.findById(id);
    }

    public Flux<Branch> findByFranchiseId(Long franchiseId) {
        return branchPort.findByFranchiseId(franchiseId);
    }

    public Mono<Branch> update(Long id, Branch branch) {
        return branchPort.update(id, branch);
    }

    public Mono<Void> delete(Long id) {
        return branchPort.delete(id);
    }
}
