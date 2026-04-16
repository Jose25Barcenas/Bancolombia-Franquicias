package com.bancolombia.franquicias.application.mapper;

import com.bancolombia.franquicias.application.dto.BranchDTO;
import com.bancolombia.franquicias.domain.model.Branch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BranchDTOMapperTest {
    private BranchDTOMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new BranchDTOMapper();
    }

    @Test
    void testToDomain() {
        BranchDTO dto = BranchDTO.builder().id(1L).franchiseId(1L).name("Test").build();
        
        Branch domain = mapper.toDomain(dto);
        
        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertEquals(1L, domain.getFranchiseId());
        assertEquals("Test", domain.getName());
    }

    @Test
    void testToDTO() {
        Branch domain = Branch.builder().id(1L).franchiseId(1L).name("Test").build();
        
        BranchDTO dto = mapper.toDTO(domain);
        
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals(1L, dto.getFranchiseId());
        assertEquals("Test", dto.getName());
    }
}
