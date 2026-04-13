package com.bancolombia.franquicias.application.service;

import com.bancolombia.franquicias.domain.model.Sucursal;
import com.bancolombia.franquicias.domain.port.SucursalPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SucursalService {
    private final SucursalPort sucursalPort;

    public Mono<Sucursal> crear(Sucursal sucursal) {
        return sucursalPort.guardar(sucursal);
    }

    public Mono<Sucursal> obtenerPorId(Long id) {
        return sucursalPort.obtenerPorId(id);
    }

    public Flux<Sucursal> obtenerPorFranquicia(Long franquiciaId) {
        return sucursalPort.obtenerPorFranquicia(franquiciaId);
    }

    public Mono<Sucursal> actualizar(Long id, Sucursal sucursal) {
        return sucursalPort.actualizar(id, sucursal);
    }

    public Mono<Void> eliminar(Long id) {
        return sucursalPort.eliminar(id);
    }
}
