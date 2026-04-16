package com.bancolombia.franquicias.infrastructure.adapter.output.persistence.mapper;

import com.bancolombia.franquicias.domain.model.Product;
import com.bancolombia.franquicias.infrastructure.adapter.output.persistence.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toDomain(ProductEntity entity) {
        return Product.builder()
                .id(entity.getId())
                .branchId(entity.getBranchId())
                .name(entity.getName())
                .stock(entity.getStock())
                .build();
    }

    public ProductEntity toEntity(Product domain) {
        return ProductEntity.builder()
                .id(domain.getId())
                .branchId(domain.getBranchId())
                .name(domain.getName())
                .stock(domain.getStock())
                .build();
    }
}
