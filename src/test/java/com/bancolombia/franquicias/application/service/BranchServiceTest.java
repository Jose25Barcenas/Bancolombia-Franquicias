package com.bancolombia.franquicias.application.service;

import com.bancolombia.franquicias.domain.model.Branch;
import com.bancolombia.franquicias.domain.port.BranchPort;
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
class BranchServiceTest {
    @Mock
    private BranchPort branchPort;
    private BranchService branchService;

    @BeforeEach
    void setUp() {
        branchService = new BranchService(branchPort);
    }

    @Test
    void testCreate() {
        Branch branch = Branch.builder().franchiseId(1L).name("Test Branch").build();
        Branch saved = Branch.builder().id(1L).franchiseId(1L).name("Test Branch").build();

        when(branchPort.save(any(Branch.class))).thenReturn(Mono.just(saved));

        StepVerifier.create(branchService.create(branch))
                .expectNext(saved)
                .verifyComplete();
    }

    @Test
    void testFindById() {
        Branch branch = Branch.builder().id(1L).franchiseId(1L).name("Test Branch").build();

        when(branchPort.findById(1L)).thenReturn(Mono.just(branch));

        StepVerifier.create(branchService.findById(1L))
                .expectNext(branch)
                .verifyComplete();
    }

    @Test
    void testFindByFranchiseId() {
        Branch branch1 = Branch.builder().id(1L).franchiseId(1L).name("Branch 1").build();
        Branch branch2 = Branch.builder().id(2L).franchiseId(1L).name("Branch 2").build();

        when(branchPort.findByFranchiseId(1L)).thenReturn(Flux.just(branch1, branch2));

        StepVerifier.create(branchService.findByFranchiseId(1L))
                .expectNext(branch1, branch2)
                .verifyComplete();
    }

    @Test
    void testUpdate() {
        Branch branch = Branch.builder().name("Updated Branch").build();
        Branch updated = Branch.builder().id(1L).franchiseId(1L).name("Updated Branch").build();

        when(branchPort.update(1L, branch)).thenReturn(Mono.just(updated));

        StepVerifier.create(branchService.update(1L, branch))
                .expectNext(updated)
                .verifyComplete();
    }

    @Test
    void testDelete() {
        when(branchPort.delete(1L)).thenReturn(Mono.empty());

        StepVerifier.create(branchService.delete(1L))
                .verifyComplete();
    }
}
