package com.bancolombia.franquicias.infrastructure.adapter.input.rest;

import com.bancolombia.franquicias.application.dto.FranchiseDTO;
import com.bancolombia.franquicias.application.mapper.FranchiseDTOMapper;
import com.bancolombia.franquicias.application.service.FranchiseService;
import com.bancolombia.franquicias.domain.model.Franchise;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FranchiseControllerTest {
    @Mock
    private FranchiseService franchiseService;
    @Mock
    private FranchiseDTOMapper mapper;
    
    private FranchiseController controller;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        controller = new FranchiseController(franchiseService, mapper);
        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    void testCreateSuccess() {
        FranchiseDTO dto = FranchiseDTO.builder().name("Test Franchise").build();
        Franchise franchise = Franchise.builder().name("Test Franchise").build();
        Franchise saved = Franchise.builder().id(1L).name("Test Franchise").build();
        FranchiseDTO responseDTO = FranchiseDTO.builder().id(1L).name("Test Franchise").build();

        when(mapper.toDomain(dto)).thenReturn(franchise);
        when(franchiseService.create(any(Franchise.class))).thenReturn(Mono.just(saved));
        when(mapper.toDTO(saved)).thenReturn(responseDTO);

        webTestClient.post()
                .uri("/api/franchises")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(FranchiseDTO.class)
                .isEqualTo(responseDTO);
    }

    @Test
    void testFindByIdSuccess() {
        Franchise franchise = Franchise.builder().id(1L).name("Test Franchise").build();
        FranchiseDTO responseDTO = FranchiseDTO.builder().id(1L).name("Test Franchise").build();

        when(franchiseService.findById(1L)).thenReturn(Mono.just(franchise));
        when(mapper.toDTO(franchise)).thenReturn(responseDTO);

        webTestClient.get()
                .uri("/api/franchises/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(FranchiseDTO.class)
                .isEqualTo(responseDTO);
    }

    @Test
    void testFindByIdNotFound() {
        when(franchiseService.findById(1L))
                .thenReturn(Mono.error(new ResourceNotFoundException("Franchise not found")));

        webTestClient.get()
                .uri("/api/franchises/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testFindAllSuccess() {
        Franchise franchise1 = Franchise.builder().id(1L).name("Franchise 1").build();
        Franchise franchise2 = Franchise.builder().id(2L).name("Franchise 2").build();
        FranchiseDTO dto1 = FranchiseDTO.builder().id(1L).name("Franchise 1").build();
        FranchiseDTO dto2 = FranchiseDTO.builder().id(2L).name("Franchise 2").build();

        when(franchiseService.findAll()).thenReturn(Flux.just(franchise1, franchise2));
        when(mapper.toDTO(franchise1)).thenReturn(dto1);
        when(mapper.toDTO(franchise2)).thenReturn(dto2);

        webTestClient.get()
                .uri("/api/franchises")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FranchiseDTO.class)
                .hasSize(2);
    }

    @Test
    void testDeleteSuccess() {
        when(franchiseService.delete(1L)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/franchises/1")
                .exchange()
                .expectStatus().isNoContent();
    }
}
