package com.bancolombia.franquicias.infrastructure.adapter.output.persistence;

import com.bancolombia.franquicias.domain.model.Sucursal;
import com.bancolombia.franquicias.domain.port.SucursalPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SucursalAdapter implements SucursalPort {
    private final SucursalRepository repository;

    @Override
    public Mono<Sucursal> guardar(Sucursal sucursal) {
        SucursalEntity entity = SucursalEntity.builder().franquiciaId(sucursal.getFranquiciaId()).nombre(sucursal.getNombre()).build();
        return repository.save(entity).map(this::toDomain);
    }

    @Override
    public Mono<Sucursal> obtenerPorId(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Flux<Sucursal> obtenerPorFranquicia(Long franquiciaId) {
        return repository.findByFranquiciaId(franquiciaId).map(this::toDomain);
    }

    @Override
    public Mono<Sucursal> actualizar(Long id, Sucursal sucursal) {
        return repository.findById(id)
                .flatMap(entity -> {
                    entity.setNombre(sucursal.getNombre());
                    return repository.save(entity);
                })
                .map(this::toDomain);
    }

    @Override
    public Mono<Void> eliminar(Long id) {
        return repository.deleteById(id);
    }

    private Sucursal toDomain(SucursalEntity entity) {
        return Sucursal.builder().id(entity.getId()).franquiciaId(entity.getFranquiciaId()).nombre(entity.getNombre()).build();
    }
}
