package com.bancolombia.franquicias.infrastructure.adapter.output.persistence.mapper;

import com.bancolombia.franquicias.domain.model.Branch;
import com.bancolombia.franquicias.infrastructure.adapter.output.persistence.BranchEntity;
import org.springframework.stereotype.Component;

@Component
public class BranchMapper {
    public Branch toDomain(BranchEntity entity) {
        return Branch.builder()
                .id(entity.getId())
                .franchiseId(entity.getFranchiseId())
                .name(entity.getName())
                .build();
    }

    public BranchEntity toEntity(Branch domain) {
        return BranchEntity.builder()
                .id(domain.getId())
                .franchiseId(domain.getFranchiseId())
                .name(domain.getName())
                .build();
    }
}
