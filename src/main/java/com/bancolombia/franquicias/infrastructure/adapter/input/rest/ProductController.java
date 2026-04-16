package com.bancolombia.franquicias.infrastructure.adapter.input.rest;

import com.bancolombia.franquicias.application.dto.ProductDTO;
import com.bancolombia.franquicias.application.dto.ProductStockDTO;
import com.bancolombia.franquicias.application.service.ProductService;
import com.bancolombia.franquicias.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public Mono<ResponseEntity<ProductDTO>> create(@RequestBody ProductDTO dto) {
        Product product = Product.builder().branchId(dto.getBranchId()).name(dto.getName()).stock(dto.getStock()).build();
        return productService.create(product)
                .map(p -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(ProductDTO.builder().id(p.getId()).branchId(p.getBranchId()).name(p.getName()).stock(p.getStock()).build()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDTO>> findById(@PathVariable Long id) {
        return productService.findById(id)
                .map(p -> ResponseEntity.ok(ProductDTO.builder().id(p.getId()).branchId(p.getBranchId()).name(p.getName()).stock(p.getStock()).build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/branch/{branchId}")
    public Flux<ProductDTO> findByBranchId(@PathVariable Long branchId) {
        return productService.findByBranchId(branchId)
                .map(p -> ProductDTO.builder().id(p.getId()).branchId(p.getBranchId()).name(p.getName()).stock(p.getStock()).build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductDTO>> update(@PathVariable Long id, @RequestBody ProductDTO dto) {
        Product product = Product.builder().name(dto.getName()).build();
        return productService.update(id, product)
                .map(p -> ResponseEntity.ok(ProductDTO.builder().id(p.getId()).branchId(p.getBranchId()).name(p.getName()).stock(p.getStock()).build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/stock")
    public Mono<ResponseEntity<ProductDTO>> updateStock(@PathVariable Long id, @RequestParam Integer newStock) {
        return productService.updateStock(id, newStock)
                .map(p -> ResponseEntity.ok(ProductDTO.builder().id(p.getId()).branchId(p.getBranchId()).name(p.getName()).stock(p.getStock()).build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return productService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/franchise/{franchiseId}/max-stock")
    public Flux<ProductStockDTO> findProductsMaxStockByFranchise(@PathVariable Long franchiseId) {
        return productService.findProductsMaxStockByFranchise(franchiseId)
                .map(ps -> ProductStockDTO.builder()
                        .productId(ps.getProductId())
                        .productName(ps.getProductName())
                        .branchId(ps.getBranchId())
                        .branchName(ps.getBranchName())
                        .stock(ps.getStock())
                        .build());
    }
}
