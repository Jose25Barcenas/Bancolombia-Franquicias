package com.bancolombia.franquicias.domain.port;

import com.bancolombia.franquicias.domain.model.Franquicia;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranquiciaPort {
    Mono<Franquicia> guardar(Franquicia franquicia);
    Mono<Franquicia> obtenerPorId(Long id);
    Flux<Franquicia> obtenerTodas();
    Mono<Franquicia> actualizar(Long id, Franquicia franquicia);
    Mono<Void> eliminar(Long id);
}
