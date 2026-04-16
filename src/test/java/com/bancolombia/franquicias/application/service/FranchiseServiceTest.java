package com.bancolombia.franquicias.application.service;

import com.bancolombia.franquicias.domain.model.Franchise;
import com.bancolombia.franquicias.domain.port.FranchisePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FranchiseServiceTest {
    @Mock
    private FranchisePort franchisePort;
    private FranchiseService franchiseService;

    @BeforeEach
    void setUp() {
        franchiseService = new FranchiseService(franchisePort);
    }

    @Test
    void testCreate() {
        Franchise franchise = Franchise.builder().name("Test Franchise").build();
        Franchise saved = Franchise.builder().id(1L).name("Test Franchise").build();

        when(franchisePort.save(any(Franchise.class))).thenReturn(Mono.just(saved));

        StepVerifier.create(franchiseService.create(franchise))
                .expectNext(saved)
                .verifyComplete();
    }

    @Test
    void testFindById() {
        Franchise franchise = Franchise.builder().id(1L).name("Test Franchise").build();

        when(franchisePort.findById(1L)).thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseService.findById(1L))
                .expectNext(franchise)
                .verifyComplete();
    }

    @Test
    void testFindAll() {
        Franchise franchise1 = Franchise.builder().id(1L).name("Franchise 1").build();
        Franchise franchise2 = Franchise.builder().id(2L).name("Franchise 2").build();

        when(franchisePort.findAll()).thenReturn(Flux.just(franchise1, franchise2));

        StepVerifier.create(franchiseService.findAll())
                .expectNext(franchise1, franchise2)
                .verifyComplete();
    }

    @Test
    void testUpdate() {
        Franchise franchise = Franchise.builder().name("Updated Franchise").build();
        Franchise updated = Franchise.builder().id(1L).name("Updated Franchise").build();

        when(franchisePort.update(1L, franchise)).thenReturn(Mono.just(updated));

        StepVerifier.create(franchiseService.update(1L, franchise))
                .expectNext(updated)
                .verifyComplete();
    }

    @Test
    void testDelete() {
        when(franchisePort.delete(1L)).thenReturn(Mono.empty());

        StepVerifier.create(franchiseService.delete(1L))
                .verifyComplete();
    }
}
