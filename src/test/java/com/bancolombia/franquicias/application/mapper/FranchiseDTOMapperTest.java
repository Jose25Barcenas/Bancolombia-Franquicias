package com.bancolombia.franquicias.application.mapper;

import com.bancolombia.franquicias.application.dto.FranchiseDTO;
import com.bancolombia.franquicias.domain.model.Franchise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FranchiseDTOMapperTest {
    private FranchiseDTOMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new FranchiseDTOMapper();
    }

    @Test
    void testToDomain() {
        FranchiseDTO dto = FranchiseDTO.builder().id(1L).name("Test").build();
        
        Franchise domain = mapper.toDomain(dto);
        
        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertEquals("Test", domain.getName());
    }

    @Test
    void testToDTO() {
        Franchise domain = Franchise.builder().id(1L).name("Test").build();
        
        FranchiseDTO dto = mapper.toDTO(domain);
        
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Test", dto.getName());
    }
}
