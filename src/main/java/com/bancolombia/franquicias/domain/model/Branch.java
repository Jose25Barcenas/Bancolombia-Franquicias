package com.bancolombia.franquicias.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch {
    private Long id;
    private Long franchiseId;
    private String name;
}
