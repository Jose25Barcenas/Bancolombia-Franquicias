package com.bancolombia.franquicias.infrastructure.adapter.input.rest;

import com.bancolombia.franquicias.application.dto.BranchDTO;
import com.bancolombia.franquicias.application.service.BranchService;
import com.bancolombia.franquicias.domain.model.Branch;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;

    @PostMapping
    public Mono<ResponseEntity<BranchDTO>> create(@RequestBody BranchDTO dto) {
        Branch branch = Branch.builder().franchiseId(dto.getFranchiseId()).name(dto.getName()).build();
        return branchService.create(branch)
                .map(s -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(BranchDTO.builder().id(s.getId()).franchiseId(s.getFranchiseId()).name(s.getName()).build()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<BranchDTO>> findById(@PathVariable Long id) {
        return branchService.findById(id)
                .map(s -> ResponseEntity.ok(BranchDTO.builder().id(s.getId()).franchiseId(s.getFranchiseId()).name(s.getName()).build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/franchise/{franchiseId}")
    public Flux<BranchDTO> findByFranchiseId(@PathVariable Long franchiseId) {
        return branchService.findByFranchiseId(franchiseId)
                .map(s -> BranchDTO.builder().id(s.getId()).franchiseId(s.getFranchiseId()).name(s.getName()).build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<BranchDTO>> update(@PathVariable Long id, @RequestBody BranchDTO dto) {
        Branch branch = Branch.builder().name(dto.getName()).build();
        return branchService.update(id, branch)
                .map(s -> ResponseEntity.ok(BranchDTO.builder().id(s.getId()).franchiseId(s.getFranchiseId()).name(s.getName()).build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return branchService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }
}
