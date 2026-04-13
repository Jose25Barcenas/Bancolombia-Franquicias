package com.bancolombia.franquicias.domain.port;

import com.bancolombia.franquicias.domain.model.Sucursal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SucursalPort {
    Mono<Sucursal> guardar(Sucursal sucursal);
    Mono<Sucursal> obtenerPorId(Long id);
    Flux<Sucursal> obtenerPorFranquicia(Long franquiciaId);
    Mono<Sucursal> actualizar(Long id, Sucursal sucursal);
    Mono<Void> eliminar(Long id);
}
