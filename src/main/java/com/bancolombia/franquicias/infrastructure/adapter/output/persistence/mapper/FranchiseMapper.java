package com.bancolombia.franquicias.infrastructure.adapter.output.persistence.mapper;

import com.bancolombia.franquicias.domain.model.Franchise;
import com.bancolombia.franquicias.infrastructure.adapter.output.persistence.FranchiseEntity;
import org.springframework.stereotype.Component;

@Component
public class FranchiseMapper {
    public Franchise toDomain(FranchiseEntity entity) {
        return Franchise.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public FranchiseEntity toEntity(Franchise domain) {
        return FranchiseEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .build();
    }
}
