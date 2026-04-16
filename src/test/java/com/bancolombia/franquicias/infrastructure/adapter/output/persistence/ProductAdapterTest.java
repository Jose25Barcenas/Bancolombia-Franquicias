package com.bancolombia.franquicias.infrastructure.adapter.output.persistence;

import com.bancolombia.franquicias.domain.model.Product;
import com.bancolombia.franquicias.infrastructure.adapter.output.persistence.mapper.ProductMapper;
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
class ProductAdapterTest {
    @Mock
    private ProductRepository repository;
    @Mock
    private ProductMapper mapper;
    private ProductAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new ProductAdapter(repository, null, mapper);
    }

    @Test
    void testSave() {
        Product product = Product.builder().branchId(1L).name("Test").stock(10).build();
        ProductEntity entity = ProductEntity.builder().branchId(1L).name("Test").stock(10).build();
        ProductEntity savedEntity = ProductEntity.builder().id(1L).branchId(1L).name("Test").stock(10).build();
        Product saved = Product.builder().id(1L).branchId(1L).name("Test").stock(10).build();

        when(mapper.toEntity(product)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(Mono.just(savedEntity));
        when(mapper.toDomain(savedEntity)).thenReturn(saved);

        StepVerifier.create(adapter.save(product))
                .expectNext(saved)
                .verifyComplete();
    }

    @Test
    void testFindById() {
        ProductEntity entity = ProductEntity.builder().id(1L).branchId(1L).name("Test").stock(10).build();
        Product product = Product.builder().id(1L).branchId(1L).name("Test").stock(10).build();

        when(repository.findById(1L)).thenReturn(Mono.just(entity));
        when(mapper.toDomain(entity)).thenReturn(product);

        StepVerifier.create(adapter.findById(1L))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void testFindByBranchId() {
        ProductEntity entity = ProductEntity.builder().id(1L).branchId(1L).name("Test").stock(10).build();
        Product product = Product.builder().id(1L).branchId(1L).name("Test").stock(10).build();

        when(repository.findByBranchId(1L)).thenReturn(Flux.just(entity));
        when(mapper.toDomain(entity)).thenReturn(product);

        StepVerifier.create(adapter.findByBranchId(1L))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void testUpdateStock() {
        ProductEntity entity = ProductEntity.builder().id(1L).branchId(1L).name("Test").stock(10).build();
        ProductEntity updated = ProductEntity.builder().id(1L).branchId(1L).name("Test").stock(50).build();
        Product product = Product.builder().id(1L).branchId(1L).name("Test").stock(50).build();

        when(repository.findById(1L)).thenReturn(Mono.just(entity));
        when(repository.save(any())).thenReturn(Mono.just(updated));
        when(mapper.toDomain(updated)).thenReturn(product);

        StepVerifier.create(adapter.updateStock(1L, 50))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void testDelete() {
        when(repository.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(adapter.delete(1L))
                .verifyComplete();
    }
}
