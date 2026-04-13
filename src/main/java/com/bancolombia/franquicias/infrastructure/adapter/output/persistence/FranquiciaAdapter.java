package com.bancolombia.franquicias.infrastructure.adapter.output.persistence;

import com.bancolombia.franquicias.domain.model.Franquicia;
import com.bancolombia.franquicias.domain.port.FranquiciaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FranquiciaAdapter implements FranquiciaPort {
    private final FranquiciaRepository repository;

    @Override
    public Mono<Franquicia> guardar(Franquicia franquicia) {
        FranquiciaEntity entity = FranquiciaEntity.builder().nombre(franquicia.getNombre()).build();
        return repository.save(entity).map(this::toDomain);
    }

    @Override
    public Mono<Franquicia> obtenerPorId(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Flux<Franquicia> obtenerTodas() {
        return repository.findAll().map(this::toDomain);
    }

    @Override
    public Mono<Franquicia> actualizar(Long id, Franquicia franquicia) {
        return repository.findById(id)
                .flatMap(entity -> {
                    entity.setNombre(franquicia.getNombre());
                    return repository.save(entity);
                })
                .map(this::toDomain);
    }

    @Override
    public Mono<Void> eliminar(Long id) {
        return repository.deleteById(id);
    }

    private Franquicia toDomain(FranquiciaEntity entity) {
        return Franquicia.builder().id(entity.getId()).nombre(entity.getNombre()).build();
    }
}
