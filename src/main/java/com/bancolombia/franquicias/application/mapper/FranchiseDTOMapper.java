package com.bancolombia.franquicias.application.mapper;

import com.bancolombia.franquicias.application.dto.FranchiseDTO;
import com.bancolombia.franquicias.domain.model.Franchise;
import org.springframework.stereotype.Component;

@Component
public class FranchiseDTOMapper {
    public FranchiseDTO toDTO(Franchise franchise) {
        return FranchiseDTO.builder()
                .id(franchise.getId())
                .name(franchise.getName())
                .build();
    }

    public Franchise toDomain(FranchiseDTO dto) {
        return Franchise.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}
