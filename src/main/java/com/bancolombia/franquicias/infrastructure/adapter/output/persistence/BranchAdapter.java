package com.bancolombia.franquicias.infrastructure.adapter.output.persistence;

import com.bancolombia.franquicias.domain.model.Branch;
import com.bancolombia.franquicias.domain.port.BranchPort;
import com.bancolombia.franquicias.infrastructure.adapter.output.persistence.mapper.BranchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BranchAdapter implements BranchPort {
    private final BranchRepository repository;
    private final BranchMapper mapper;

    @Override
    public Mono<Branch> save(Branch branch) {
        BranchEntity entity = mapper.toEntity(branch);
        return repository.save(entity).map(mapper::toDomain);
    }

    @Override
    public Mono<Branch> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Flux<Branch> findByFranchiseId(Long franchiseId) {
        return repository.findByFranchiseId(franchiseId).map(mapper::toDomain);
    }

    @Override
    public Mono<Branch> update(Long id, Branch branch) {
        return repository.findById(id)
                .flatMap(entity -> {
                    entity.setName(branch.getName());
                    return repository.save(entity);
                })
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }
}
