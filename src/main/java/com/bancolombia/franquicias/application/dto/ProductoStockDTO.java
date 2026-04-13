package com.bancolombia.franquicias.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoStockDTO {
    private Long productoId;
    private String nombreProducto;
    private Long sucursalId;
    private String nombreSucursal;
    private Integer stock;
}
