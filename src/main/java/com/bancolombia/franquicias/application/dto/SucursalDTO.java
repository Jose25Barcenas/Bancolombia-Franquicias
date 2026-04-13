package com.bancolombia.franquicias.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SucursalDTO {
    private Long id;
    private Long franquiciaId;
    private String nombre;
}
