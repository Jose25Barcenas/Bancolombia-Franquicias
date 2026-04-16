package com.bancolombia.franquicias.infrastructure.adapter.output.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("franchises")
public class FranchiseEntity {
    @Id
    private Long id;
    private String name;
}
