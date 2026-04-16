package com.bancolombia.franquicias.infrastructure.adapter.output.persistence;

import com.bancolombia.franquicias.domain.model.Franchise;
import com.bancolombia.franquicias.domain.port.FranchisePort;
import com.bancolombia.franquicias.infrastructure.adapter.output.persistence.mapper.FranchiseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FranchiseAdapter implements FranchisePort {
    private final FranchiseRepository repository;
    private final FranchiseMapper mapper;

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        FranchiseEntity entity = mapper.toEntity(franchise);
        return repository.save(entity).map(mapper::toDomain);
    }

    @Override
    public Mono<Franchise> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Flux<Franchise> findAll() {
        return repository.findAll().map(mapper::toDomain);
    }

    @Override
    public Mono<Franchise> update(Long id, Franchise franchise) {
        return repository.findById(id)
                .flatMap(entity -> {
                    entity.setName(franchise.getName());
                    return repository.save(entity);
                })
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }
}
