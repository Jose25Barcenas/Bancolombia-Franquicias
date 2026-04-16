package com.bancolombia.franquicias.infrastructure.adapter.input.rest;

import com.bancolombia.franquicias.application.dto.ProductDTO;
import com.bancolombia.franquicias.application.mapper.ProductDTOMapper;
import com.bancolombia.franquicias.application.service.ProductService;
import com.bancolombia.franquicias.domain.model.Product;
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
class ProductControllerTest {
    @Mock
    private ProductService productService;
    @Mock
    private ProductDTOMapper mapper;
    
    private ProductController controller;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        controller = new ProductController(productService, mapper);
        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    void testCreateSuccess() {
        ProductDTO dto = ProductDTO.builder().branchId(1L).name("Test Product").stock(10).build();
        Product product = Product.builder().branchId(1L).name("Test Product").stock(10).build();
        Product saved = Product.builder().id(1L).branchId(1L).name("Test Product").stock(10).build();
        ProductDTO responseDTO = ProductDTO.builder().id(1L).branchId(1L).name("Test Product").stock(10).build();

        when(mapper.toDomain(dto)).thenReturn(product);
        when(productService.create(any(Product.class))).thenReturn(Mono.just(saved));
        when(mapper.toDTO(saved)).thenReturn(responseDTO);

        webTestClient.post()
                .uri("/api/products")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void testFindByIdSuccess() {
        Product product = Product.builder().id(1L).branchId(1L).name("Test Product").stock(10).build();
        ProductDTO responseDTO = ProductDTO.builder().id(1L).branchId(1L).name("Test Product").stock(10).build();

        when(productService.findById(1L)).thenReturn(Mono.just(product));
        when(mapper.toDTO(product)).thenReturn(responseDTO);

        webTestClient.get()
                .uri("/api/products/1")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testFindByBranchIdSuccess() {
        Product product = Product.builder().id(1L).branchId(1L).name("Test Product").stock(10).build();
        ProductDTO dto = ProductDTO.builder().id(1L).branchId(1L).name("Test Product").stock(10).build();

        when(productService.findByBranchId(1L)).thenReturn(Flux.just(product));
        when(mapper.toDTO(product)).thenReturn(dto);

        webTestClient.get()
                .uri("/api/products/branch/1")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testUpdateStockSuccess() {
        Product updated = Product.builder().id(1L).branchId(1L).name("Test Product").stock(50).build();
        ProductDTO responseDTO = ProductDTO.builder().id(1L).branchId(1L).name("Test Product").stock(50).build();

        when(productService.updateStock(1L, 50)).thenReturn(Mono.just(updated));
        when(mapper.toDTO(updated)).thenReturn(responseDTO);

        webTestClient.patch()
                .uri("/api/products/1/stock?newStock=50")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testUpdateStockNegativeValue() {
        webTestClient.patch()
                .uri("/api/products/1/stock?newStock=-10")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testDeleteSuccess() {
        when(productService.delete(1L)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/products/1")
                .exchange()
                .expectStatus().isNoContent();
    }
}
