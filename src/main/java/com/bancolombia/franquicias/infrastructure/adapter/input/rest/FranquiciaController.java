package com.bancolombia.franquicias.infrastructure.adapter.input.rest;

import com.bancolombia.franquicias.application.dto.FranquiciaDTO;
import com.bancolombia.franquicias.application.service.FranquiciaService;
import com.bancolombia.franquicias.domain.model.Franquicia;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franquicias")
@RequiredArgsConstructor
public class FranquiciaController {
    private final FranquiciaService franquiciaService;

    @PostMapping
    public Mono<ResponseEntity<FranquiciaDTO>> crear(@RequestBody FranquiciaDTO dto) {
        Franquicia franquicia = Franquicia.builder().nombre(dto.getNombre()).build();
        return franquiciaService.crear(franquicia)
                .map(f -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(FranquiciaDTO.builder().id(f.getId()).nombre(f.getNombre()).build()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<FranquiciaDTO>> obtenerPorId(@PathVariable Long id) {
        return franquiciaService.obtenerPorId(id)
                .map(f -> ResponseEntity.ok(FranquiciaDTO.builder().id(f.getId()).nombre(f.getNombre()).build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<FranquiciaDTO> obtenerTodas() {
        return franquiciaService.obtenerTodas()
                .map(f -> FranquiciaDTO.builder().id(f.getId()).nombre(f.getNombre()).build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<FranquiciaDTO>> actualizar(@PathVariable Long id, @RequestBody FranquiciaDTO dto) {
        Franquicia franquicia = Franquicia.builder().nombre(dto.getNombre()).build();
        return franquiciaService.actualizar(id, franquicia)
                .map(f -> ResponseEntity.ok(FranquiciaDTO.builder().id(f.getId()).nombre(f.getNombre()).build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable Long id) {
        return franquiciaService.eliminar(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }
}
