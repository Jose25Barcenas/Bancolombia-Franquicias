package com.bancolombia.franquicias.infrastructure.adapter.input.rest;

import com.bancolombia.franquicias.application.dto.FranchiseDTO;
import com.bancolombia.franquicias.application.mapper.FranchiseDTOMapper;
import com.bancolombia.franquicias.application.service.FranchiseService;
import com.bancolombia.franquicias.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/franchises")
@RequiredArgsConstructor
public class FranchiseController {
    private final FranchiseService franchiseService;
    private final FranchiseDTOMapper mapper;

    @PostMapping
    public Mono<ResponseEntity<FranchiseDTO>> create(@RequestBody FranchiseDTO dto) {
        log.info("Creating franchise: {}", dto.getName());
        return franchiseService.create(mapper.toDomain(dto))
                .map(mapper::toDTO)
                .map(f -> ResponseEntity.status(HttpStatus.CREATED).body(f))
                .doOnError(e -> log.error("Error creating franchise", e));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<FranchiseDTO>> findById(@PathVariable Long id) {
        log.info("Finding franchise by id: {}", id);
        return franchiseService.findById(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Franchise not found with id: " + id)))
                .onErrorResume(e -> Mono.error(e));
    }

    @GetMapping
    public Flux<FranchiseDTO> findAll() {
        log.info("Finding all franchises");
        return franchiseService.findAll()
                .map(mapper::toDTO)
                .doOnError(e -> log.error("Error finding franchises", e));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<FranchiseDTO>> update(@PathVariable Long id, @RequestBody FranchiseDTO dto) {
        log.info("Updating franchise with id: {}", id);
        return franchiseService.update(id, mapper.toDomain(dto))
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Franchise not found with id: " + id)))
                .doOnError(e -> log.error("Error updating franchise", e));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        log.info("Deleting franchise with id: {}", id);
        return franchiseService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .doOnError(e -> log.error("Error deleting franchise", e));
    }
}
