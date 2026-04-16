package com.bancolombia.franquicias.application.mapper;

import com.bancolombia.franquicias.application.dto.ProductDTO;
import com.bancolombia.franquicias.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductDTOMapperTest {
    private ProductDTOMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ProductDTOMapper();
    }

    @Test
    void testToDomain() {
        ProductDTO dto = ProductDTO.builder().id(1L).branchId(1L).name("Test").stock(10).build();
        
        Product domain = mapper.toDomain(dto);
        
        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertEquals(1L, domain.getBranchId());
        assertEquals("Test", domain.getName());
        assertEquals(10, domain.getStock());
    }

    @Test
    void testToDTO() {
        Product domain = Product.builder().id(1L).branchId(1L).name("Test").stock(10).build();
        
        ProductDTO dto = mapper.toDTO(domain);
        
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals(1L, dto.getBranchId());
        assertEquals("Test", dto.getName());
        assertEquals(10, dto.getStock());
    }
}
