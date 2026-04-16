package com.bancolombia.franquicias.infrastructure.adapter.input.rest;

import com.bancolombia.franquicias.application.dto.FranchiseDTO;
import com.bancolombia.franquicias.application.service.FranchiseService;
import com.bancolombia.franquicias.domain.model.Franchise;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franchises")
@RequiredArgsConstructor
public class FranchiseController {
    private final FranchiseService franchiseService;

    @PostMapping
    public Mono<ResponseEntity<FranchiseDTO>> create(@RequestBody FranchiseDTO dto) {
        Franchise franchise = Franchise.builder().name(dto.getName()).build();
        return franchiseService.create(franchise)
                .map(f -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(FranchiseDTO.builder().id(f.getId()).name(f.getName()).build()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<FranchiseDTO>> findById(@PathVariable Long id) {
        return franchiseService.findById(id)
                .map(f -> ResponseEntity.ok(FranchiseDTO.builder().id(f.getId()).name(f.getName()).build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<FranchiseDTO> findAll() {
        return franchiseService.findAll()
                .map(f -> FranchiseDTO.builder().id(f.getId()).name(f.getName()).build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<FranchiseDTO>> update(@PathVariable Long id, @RequestBody FranchiseDTO dto) {
        Franchise franchise = Franchise.builder().name(dto.getName()).build();
        return franchiseService.update(id, franchise)
                .map(f -> ResponseEntity.ok(FranchiseDTO.builder().id(f.getId()).name(f.getName()).build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return franchiseService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }
}
