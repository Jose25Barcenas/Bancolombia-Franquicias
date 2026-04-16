package com.bancolombia.franquicias.application.service;

import com.bancolombia.franquicias.domain.model.Product;
import com.bancolombia.franquicias.domain.model.ProductStock;
import com.bancolombia.franquicias.domain.port.ProductPort;
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
class ProductServiceTest {
    @Mock
    private ProductPort productPort;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productPort);
    }

    @Test
    void testCreate() {
        Product product = Product.builder().branchId(1L).name("Test Product").stock(10).build();
        Product saved = Product.builder().id(1L).branchId(1L).name("Test Product").stock(10).build();

        when(productPort.save(any(Product.class))).thenReturn(Mono.just(saved));

        StepVerifier.create(productService.create(product))
                .expectNext(saved)
                .verifyComplete();
    }

    @Test
    void testFindById() {
        Product product = Product.builder().id(1L).branchId(1L).name("Test Product").stock(10).build();

        when(productPort.findById(1L)).thenReturn(Mono.just(product));

        StepVerifier.create(productService.findById(1L))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void testFindByBranchId() {
        Product product1 = Product.builder().id(1L).branchId(1L).name("Product 1").stock(10).build();
        Product product2 = Product.builder().id(2L).branchId(1L).name("Product 2").stock(20).build();

        when(productPort.findByBranchId(1L)).thenReturn(Flux.just(product1, product2));

        StepVerifier.create(productService.findByBranchId(1L))
                .expectNext(product1, product2)
                .verifyComplete();
    }

    @Test
    void testUpdateStock() {
        Product updated = Product.builder().id(1L).branchId(1L).name("Test Product").stock(50).build();

        when(productPort.updateStock(1L, 50)).thenReturn(Mono.just(updated));

        StepVerifier.create(productService.updateStock(1L, 50))
                .expectNext(updated)
                .verifyComplete();
    }

    @Test
    void testDelete() {
        when(productPort.delete(1L)).thenReturn(Mono.empty());

        StepVerifier.create(productService.delete(1L))
                .verifyComplete();
    }

    @Test
    void testFindProductsMaxStockByFranchise() {
        ProductStock ps = ProductStock.builder()
                .productId(1L)
                .productName("Product 1")
                .branchId(1L)
                .branchName("Branch 1")
                .stock(100)
                .build();

        when(productPort.findProductsMaxStockByFranchise(1L)).thenReturn(Flux.just(ps));

        StepVerifier.create(productService.findProductsMaxStockByFranchise(1L))
                .expectNext(ps)
                .verifyComplete();
    }
}
