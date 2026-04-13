package com.bancolombia.franquicias.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoStock {
    private Long productoId;
    private String nombreProducto;
    private Long sucursalId;
    private String nombreSucursal;
    private Integer stock;
}
