package com.bancolombia.franquicias.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductStockDTO {
    private Long productId;
    private String productName;
    private Long branchId;
    private String branchName;
    private Integer stock;
}
