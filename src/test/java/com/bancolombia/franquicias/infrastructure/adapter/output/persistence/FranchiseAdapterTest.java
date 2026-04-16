package com.bancolombia.franquicias.infrastructure.adapter.output.persistence;

import com.bancolombia.franquicias.domain.model.Franchise;
import com.bancolombia.franquicias.infrastructure.adapter.output.persistence.mapper.FranchiseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FranchiseAdapterTest {
    @Mock
    private FranchiseRepository repository;
    @Mock
    private FranchiseMapper mapper;
    private FranchiseAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new FranchiseAdapter(repository, mapper);
    }

    @Test
    void testSave() {
        Franchise franchise = Franchise.builder().name("Test").build();
        FranchiseEntity entity = FranchiseEntity.builder().name("Test").build();
        FranchiseEntity savedEntity = FranchiseEntity.builder().id(1L).name("Test").build();
        Franchise saved = Franchise.builder().id(1L).name("Test").build();

        when(mapper.toEntity(franchise)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(Mono.just(savedEntity));
        when(mapper.toDomain(savedEntity)).thenReturn(saved);

        StepVerifier.create(adapter.save(franchise))
                .expectNext(saved)
                .verifyComplete();
    }

    @Test
    void testFindById() {
        FranchiseEntity entity = FranchiseEntity.builder().id(1L).name("Test").build();
        Franchise franchise = Franchise.builder().id(1L).name("Test").build();

        when(repository.findById(1L)).thenReturn(Mono.just(entity));
        when(mapper.toDomain(entity)).thenReturn(franchise);

        StepVerifier.create(adapter.findById(1L))
                .expectNext(franchise)
                .verifyComplete();
    }

    @Test
    void testFindAll() {
        FranchiseEntity entity1 = FranchiseEntity.builder().id(1L).name("Test 1").build();
        FranchiseEntity entity2 = FranchiseEntity.builder().id(2L).name("Test 2").build();
        Franchise franchise1 = Franchise.builder().id(1L).name("Test 1").build();
        Franchise franchise2 = Franchise.builder().id(2L).name("Test 2").build();

        when(repository.findAll()).thenReturn(Flux.just(entity1, entity2));
        when(mapper.toDomain(entity1)).thenReturn(franchise1);
        when(mapper.toDomain(entity2)).thenReturn(franchise2);

        StepVerifier.create(adapter.findAll())
                .expectNext(franchise1, franchise2)
                .verifyComplete();
    }

    @Test
    void testDelete() {
        when(repository.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(adapter.delete(1L))
                .verifyComplete();
    }
}
