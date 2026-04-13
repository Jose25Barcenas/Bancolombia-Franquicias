package com.bancolombia.franquicias.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sucursal {
    private Long id;
    private Long franquiciaId;
    private String nombre;
}
