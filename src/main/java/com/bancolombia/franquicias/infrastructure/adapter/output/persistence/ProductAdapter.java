package com.bancolombia.franquicias.infrastructure.adapter.output.persistence;

import com.bancolombia.franquicias.domain.model.Product;
import com.bancolombia.franquicias.domain.model.ProductStock;
import com.bancolombia.franquicias.domain.port.ProductPort;
import com.bancolombia.franquicias.infrastructure.adapter.output.persistence.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductAdapter implements ProductPort {
    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;
    private final ProductMapper mapper;

    @Override
    public Mono<Product> save(Product product) {
        ProductEntity entity = mapper.toEntity(product);
        return productRepository.save(entity).map(mapper::toDomain);
    }

    @Override
    public Mono<Product> findById(Long id) {
        return productRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Flux<Product> findByBranchId(Long branchId) {
        return productRepository.findByBranchId(branchId).map(mapper::toDomain);
    }

    @Override
    public Mono<Product> update(Long id, Product product) {
        return productRepository.findById(id)
                .flatMap(entity -> {
                    entity.setName(product.getName());
                    return productRepository.save(entity);
                })
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Product> updateStock(Long id, Integer newStock) {
        return productRepository.findById(id)
                .flatMap(entity -> {
                    entity.setStock(newStock);
                    return productRepository.save(entity);
                })
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return productRepository.deleteById(id);
    }

    @Override
    public Flux<ProductStock> findProductsMaxStockByFranchise(Long franchiseId) {
        return branchRepository.findByFranchiseId(franchiseId)
                .flatMap(branch -> productRepository.findMaxStockByBranchId(branch.getId())
                        .map(product -> ProductStock.builder()
                                .productId(product.getId())
                                .productName(product.getName())
                                .branchId(branch.getId())
                                .branchName(branch.getName())
                                .stock(product.getStock())
                                .build())
                        .defaultIfEmpty(null))
                .filter(dto -> dto != null);
    }
}
