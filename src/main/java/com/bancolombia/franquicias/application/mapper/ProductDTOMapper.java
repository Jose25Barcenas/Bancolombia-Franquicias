package com.bancolombia.franquicias.application.mapper;

import com.bancolombia.franquicias.application.dto.ProductDTO;
import com.bancolombia.franquicias.domain.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDTOMapper {
    public ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .branchId(product.getBranchId())
                .name(product.getName())
                .stock(product.getStock())
                .build();
    }

    public Product toDomain(ProductDTO dto) {
        return Product.builder()
                .id(dto.getId())
                .branchId(dto.getBranchId())
                .name(dto.getName())
                .stock(dto.getStock())
                .build();
    }
}
