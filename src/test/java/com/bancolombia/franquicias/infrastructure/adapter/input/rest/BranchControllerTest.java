package com.bancolombia.franquicias.infrastructure.adapter.input.rest;

import com.bancolombia.franquicias.application.dto.BranchDTO;
import com.bancolombia.franquicias.application.mapper.BranchDTOMapper;
import com.bancolombia.franquicias.application.service.BranchService;
import com.bancolombia.franquicias.domain.model.Branch;
import com.bancolombia.franquicias.infrastructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchControllerTest {
    @Mock
    private BranchService branchService;
    @Mock
    private BranchDTOMapper mapper;
    
    private BranchController controller;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        controller = new BranchController(branchService, mapper);
        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    void testCreateSuccess() {
        BranchDTO dto = BranchDTO.builder().franchiseId(1L).name("Test Branch").build();
        Branch branch = Branch.builder().franchiseId(1L).name("Test Branch").build();
        Branch saved = Branch.builder().id(1L).franchiseId(1L).name("Test Branch").build();
        BranchDTO responseDTO = BranchDTO.builder().id(1L).franchiseId(1L).name("Test Branch").build();

        when(mapper.toDomain(dto)).thenReturn(branch);
        when(branchService.create(any(Branch.class))).thenReturn(Mono.just(saved));
        when(mapper.toDTO(saved)).thenReturn(responseDTO);

        webTestClient.post()
                .uri("/api/branches")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void testFindByIdSuccess() {
        Branch branch = Branch.builder().id(1L).franchiseId(1L).name("Test Branch").build();
        BranchDTO responseDTO = BranchDTO.builder().id(1L).franchiseId(1L).name("Test Branch").build();

        when(branchService.findById(1L)).thenReturn(Mono.just(branch));
        when(mapper.toDTO(branch)).thenReturn(responseDTO);

        webTestClient.get()
                .uri("/api/branches/1")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testFindByFranchiseIdSuccess() {
        Branch branch = Branch.builder().id(1L).franchiseId(1L).name("Test Branch").build();
        BranchDTO dto = BranchDTO.builder().id(1L).franchiseId(1L).name("Test Branch").build();

        when(branchService.findByFranchiseId(1L)).thenReturn(Flux.just(branch));
        when(mapper.toDTO(branch)).thenReturn(dto);

        webTestClient.get()
                .uri("/api/branches/franchise/1")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testDeleteSuccess() {
        when(branchService.delete(1L)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/branches/1")
                .exchange()
                .expectStatus().isNoContent();
    }
}
