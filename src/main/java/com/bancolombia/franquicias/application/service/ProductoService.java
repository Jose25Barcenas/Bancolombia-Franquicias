package com.bancolombia.franquicias.application.service;

import com.bancolombia.franquicias.domain.model.Producto;
import com.bancolombia.franquicias.domain.model.ProductoStock;
import com.bancolombia.franquicias.domain.port.ProductoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoPort productoPort;

    public Mono<Producto> crear(Producto producto) {
        return productoPort.guardar(producto);
    }

    public Mono<Producto> obtenerPorId(Long id) {
        return productoPort.obtenerPorId(id);
    }

    public Flux<Producto> obtenerPorSucursal(Long sucursalId) {
        return productoPort.obtenerPorSucursal(sucursalId);
    }

    public Mono<Producto> actualizar(Long id, Producto producto) {
        return productoPort.actualizar(id, producto);
    }

    public Mono<Producto> actualizarStock(Long id, Integer nuevoStock) {
        return productoPort.actualizarStock(id, nuevoStock);
    }

    public Mono<Void> eliminar(Long id) {
        return productoPort.eliminar(id);
    }

    public Flux<ProductoStock> obtenerProductosMaxStockPorFranquicia(Long franquiciaId) {
        return productoPort.obtenerProductosMaxStockPorFranquicia(franquiciaId);
    }
}
