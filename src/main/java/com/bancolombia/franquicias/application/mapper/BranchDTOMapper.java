package com.bancolombia.franquicias.application.mapper;

import com.bancolombia.franquicias.application.dto.BranchDTO;
import com.bancolombia.franquicias.domain.model.Branch;
import org.springframework.stereotype.Component;

@Component
public class BranchDTOMapper {
    public BranchDTO toDTO(Branch branch) {
        return BranchDTO.builder()
                .id(branch.getId())
                .franchiseId(branch.getFranchiseId())
                .name(branch.getName())
                .build();
    }

    public Branch toDomain(BranchDTO dto) {
        return Branch.builder()
                .id(dto.getId())
                .franchiseId(dto.getFranchiseId())
                .name(dto.getName())
                .build();
    }
}
