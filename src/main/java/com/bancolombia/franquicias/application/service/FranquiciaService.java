package com.bancolombia.franquicias.application.service;

import com.bancolombia.franquicias.domain.model.Franquicia;
import com.bancolombia.franquicias.domain.port.FranquiciaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FranquiciaService {
    private final FranquiciaPort franquiciaPort;

    public Mono<Franquicia> crear(Franquicia franquicia) {
        return franquiciaPort.guardar(franquicia);
    }

    public Mono<Franquicia> obtenerPorId(Long id) {
        return franquiciaPort.obtenerPorId(id);
    }

    public Flux<Franquicia> obtenerTodas() {
        return franquiciaPort.obtenerTodas();
    }

    public Mono<Franquicia> actualizar(Long id, Franquicia franquicia) {
        return franquiciaPort.actualizar(id, franquicia);
    }

    public Mono<Void> eliminar(Long id) {
        return franquiciaPort.eliminar(id);
    }
}
