package com.bancolombia.franquicias.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchDTO {
    private Long id;
    private Long franchiseId;
    private String name;
}
