package com.bancolombia.franquicias.infrastructure.adapter.output.persistence;

import com.bancolombia.franquicias.domain.model.Branch;
import com.bancolombia.franquicias.infrastructure.adapter.output.persistence.mapper.BranchMapper;
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
class BranchAdapterTest {
    @Mock
    private BranchRepository repository;
    @Mock
    private BranchMapper mapper;
    private BranchAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new BranchAdapter(repository, mapper);
    }

    @Test
    void testSave() {
        Branch branch = Branch.builder().franchiseId(1L).name("Test").build();
        BranchEntity entity = BranchEntity.builder().franchiseId(1L).name("Test").build();
        BranchEntity savedEntity = BranchEntity.builder().id(1L).franchiseId(1L).name("Test").build();
        Branch saved = Branch.builder().id(1L).franchiseId(1L).name("Test").build();

        when(mapper.toEntity(branch)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(Mono.just(savedEntity));
        when(mapper.toDomain(savedEntity)).thenReturn(saved);

        StepVerifier.create(adapter.save(branch))
                .expectNext(saved)
                .verifyComplete();
    }

    @Test
    void testFindById() {
        BranchEntity entity = BranchEntity.builder().id(1L).franchiseId(1L).name("Test").build();
        Branch branch = Branch.builder().id(1L).franchiseId(1L).name("Test").build();

        when(repository.findById(1L)).thenReturn(Mono.just(entity));
        when(mapper.toDomain(entity)).thenReturn(branch);

        StepVerifier.create(adapter.findById(1L))
                .expectNext(branch)
                .verifyComplete();
    }

    @Test
    void testFindByFranchiseId() {
        BranchEntity entity = BranchEntity.builder().id(1L).franchiseId(1L).name("Test").build();
        Branch branch = Branch.builder().id(1L).franchiseId(1L).name("Test").build();

        when(repository.findByFranchiseId(1L)).thenReturn(Flux.just(entity));
        when(mapper.toDomain(entity)).thenReturn(branch);

        StepVerifier.create(adapter.findByFranchiseId(1L))
                .expectNext(branch)
                .verifyComplete();
    }

    @Test
    void testDelete() {
        when(repository.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(adapter.delete(1L))
                .verifyComplete();
    }
}
