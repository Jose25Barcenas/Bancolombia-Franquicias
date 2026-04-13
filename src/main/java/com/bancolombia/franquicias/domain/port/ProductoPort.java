package com.bancolombia.franquicias.domain.port;

import com.bancolombia.franquicias.domain.model.Producto;
import com.bancolombia.franquicias.domain.model.ProductoStock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoPort {
    Mono<Producto> guardar(Producto producto);
    Mono<Producto> obtenerPorId(Long id);
    Flux<Producto> obtenerPorSucursal(Long sucursalId);
    Mono<Producto> actualizar(Long id, Producto producto);
    Mono<Producto> actualizarStock(Long id, Integer nuevoStock);
    Mono<Void> eliminar(Long id);
    Flux<ProductoStock> obtenerProductosMaxStockPorFranquicia(Long franquiciaId);
}
