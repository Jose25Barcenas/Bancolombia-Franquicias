package com.bancolombia.franquicias.infrastructure.adapter.input.rest;

import com.bancolombia.franquicias.application.dto.ProductoDTO;
import com.bancolombia.franquicias.application.dto.ProductoStockDTO;
import com.bancolombia.franquicias.application.service.ProductoService;
import com.bancolombia.franquicias.domain.model.Producto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;

    @PostMapping
    public Mono<ResponseEntity<ProductoDTO>> crear(@RequestBody ProductoDTO dto) {
        Producto producto = Producto.builder().sucursalId(dto.getSucursalId()).nombre(dto.getNombre()).stock(dto.getStock()).build();
        return productoService.crear(producto)
                .map(p -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(ProductoDTO.builder().id(p.getId()).sucursalId(p.getSucursalId()).nombre(p.getNombre()).stock(p.getStock()).build()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductoDTO>> obtenerPorId(@PathVariable Long id) {
        return productoService.obtenerPorId(id)
                .map(p -> ResponseEntity.ok(ProductoDTO.builder().id(p.getId()).sucursalId(p.getSucursalId()).nombre(p.getNombre()).stock(p.getStock()).build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/sucursal/{sucursalId}")
    public Flux<ProductoDTO> obtenerPorSucursal(@PathVariable Long sucursalId) {
        return productoService.obtenerPorSucursal(sucursalId)
                .map(p -> ProductoDTO.builder().id(p.getId()).sucursalId(p.getSucursalId()).nombre(p.getNombre()).stock(p.getStock()).build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductoDTO>> actualizar(@PathVariable Long id, @RequestBody ProductoDTO dto) {
        Producto producto = Producto.builder().nombre(dto.getNombre()).build();
        return productoService.actualizar(id, producto)
                .map(p -> ResponseEntity.ok(ProductoDTO.builder().id(p.getId()).sucursalId(p.getSucursalId()).nombre(p.getNombre()).stock(p.getStock()).build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/stock")
    public Mono<ResponseEntity<ProductoDTO>> actualizarStock(@PathVariable Long id, @RequestParam Integer nuevoStock) {
        return productoService.actualizarStock(id, nuevoStock)
                .map(p -> ResponseEntity.ok(ProductoDTO.builder().id(p.getId()).sucursalId(p.getSucursalId()).nombre(p.getNombre()).stock(p.getStock()).build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable Long id) {
        return productoService.eliminar(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/franquicia/{franquiciaId}/max-stock")
    public Flux<ProductoStockDTO> obtenerProductosMaxStockPorFranquicia(@PathVariable Long franquiciaId) {
        return productoService.obtenerProductosMaxStockPorFranquicia(franquiciaId);
    }
}
