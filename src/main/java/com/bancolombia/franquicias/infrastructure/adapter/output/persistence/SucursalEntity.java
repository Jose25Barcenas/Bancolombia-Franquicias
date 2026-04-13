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
@Table("sucursales")
public class SucursalEntity {
    @Id
    private Long id;
    private Long franquiciaId;
    private String nombre;
}
