package com.bancolombia.franquicias.infrastructure.adapter.input.rest;

import com.bancolombia.franquicias.application.dto.SucursalDTO;
import com.bancolombia.franquicias.application.service.SucursalService;
import com.bancolombia.franquicias.domain.model.Sucursal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/sucursales")
@RequiredArgsConstructor
public class SucursalController {
    private final SucursalService sucursalService;

    @PostMapping
    public Mono<ResponseEntity<SucursalDTO>> crear(@RequestBody SucursalDTO dto) {
        Sucursal sucursal = Sucursal.builder().franquiciaId(dto.getFranquiciaId()).nombre(dto.getNombre()).build();
        return sucursalService.crear(sucursal)
                .map(s -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(SucursalDTO.builder().id(s.getId()).franquiciaId(s.getFranquiciaId()).nombre(s.getNombre()).build()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<SucursalDTO>> obtenerPorId(@PathVariable Long id) {
        return sucursalService.obtenerPorId(id)
                .map(s -> ResponseEntity.ok(SucursalDTO.builder().id(s.getId()).franquiciaId(s.getFranquiciaId()).nombre(s.getNombre()).build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/franquicia/{franquiciaId}")
    public Flux<SucursalDTO> obtenerPorFranquicia(@PathVariable Long franquiciaId) {
        return sucursalService.obtenerPorFranquicia(franquiciaId)
                .map(s -> SucursalDTO.builder().id(s.getId()).franquiciaId(s.getFranquiciaId()).nombre(s.getNombre()).build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<SucursalDTO>> actualizar(@PathVariable Long id, @RequestBody SucursalDTO dto) {
        Sucursal sucursal = Sucursal.builder().nombre(dto.getNombre()).build();
        return sucursalService.actualizar(id, sucursal)
                .map(s -> ResponseEntity.ok(SucursalDTO.builder().id(s.getId()).franquiciaId(s.getFranquiciaId()).nombre(s.getNombre()).build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable Long id) {
        return sucursalService.eliminar(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }
}
