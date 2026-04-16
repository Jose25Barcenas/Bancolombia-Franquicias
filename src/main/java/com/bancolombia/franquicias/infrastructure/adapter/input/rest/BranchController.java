package com.bancolombia.franquicias.infrastructure.adapter.input.rest;

import com.bancolombia.franquicias.application.dto.BranchDTO;
import com.bancolombia.franquicias.application.mapper.BranchDTOMapper;
import com.bancolombia.franquicias.application.service.BranchService;
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
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;
    private final BranchDTOMapper mapper;

    @PostMapping
    public Mono<ResponseEntity<BranchDTO>> create(@RequestBody BranchDTO dto) {
        log.info("Creating branch: {}", dto.getName());
        return branchService.create(mapper.toDomain(dto))
                .map(mapper::toDTO)
                .map(b -> ResponseEntity.status(HttpStatus.CREATED).body(b))
                .doOnError(e -> log.error("Error creating branch", e));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<BranchDTO>> findById(@PathVariable Long id) {
        log.info("Finding branch by id: {}", id);
        return branchService.findById(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Branch not found with id: " + id)));
    }

    @GetMapping("/franchise/{franchiseId}")
    public Flux<BranchDTO> findByFranchiseId(@PathVariable Long franchiseId) {
        log.info("Finding branches by franchise id: {}", franchiseId);
        return branchService.findByFranchiseId(franchiseId)
                .map(mapper::toDTO)
                .doOnError(e -> log.error("Error finding branches", e));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<BranchDTO>> update(@PathVariable Long id, @RequestBody BranchDTO dto) {
        log.info("Updating branch with id: {}", id);
        return branchService.update(id, mapper.toDomain(dto))
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Branch not found with id: " + id)));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        log.info("Deleting branch with id: {}", id);
        return branchService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .doOnError(e -> log.error("Error deleting branch", e));
    }
}
