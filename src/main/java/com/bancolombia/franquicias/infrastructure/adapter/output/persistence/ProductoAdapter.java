package com.bancolombia.franquicias.infrastructure.adapter.output.persistence;

import com.bancolombia.franquicias.domain.model.Producto;
import com.bancolombia.franquicias.domain.model.ProductoStock;
import com.bancolombia.franquicias.domain.port.ProductoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductoAdapter implements ProductoPort {
    private final ProductoRepository productoRepository;
    private final SucursalRepository sucursalRepository;

    @Override
    public Mono<Producto> guardar(Producto producto) {
        ProductoEntity entity = ProductoEntity.builder().sucursalId(producto.getSucursalId()).nombre(producto.getNombre()).stock(producto.getStock()).build();
        return productoRepository.save(entity).map(this::toDomain);
    }

    @Override
    public Mono<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Flux<Producto> obtenerPorSucursal(Long sucursalId) {
        return productoRepository.findBySucursalId(sucursalId).map(this::toDomain);
    }

    @Override
    public Mono<Producto> actualizar(Long id, Producto producto) {
        return productoRepository.findById(id)
                .flatMap(entity -> {
                    entity.setNombre(producto.getNombre());
                    return productoRepository.save(entity);
                })
                .map(this::toDomain);
    }

    @Override
    public Mono<Producto> actualizarStock(Long id, Integer nuevoStock) {
        return productoRepository.findById(id)
                .flatMap(entity -> {
                    entity.setStock(nuevoStock);
                    return productoRepository.save(entity);
                })
                .map(this::toDomain);
    }

    @Override
    public Mono<Void> eliminar(Long id) {
        return productoRepository.deleteById(id);
    }

    @Override
    public Flux<ProductoStock> obtenerProductosMaxStockPorFranquicia(Long franquiciaId) {
        return sucursalRepository.findByFranquiciaId(franquiciaId)
                .flatMap(sucursal -> productoRepository.findMaxStockBySucursalId(sucursal.getId())
                        .map(producto -> ProductoStock.builder()
                                .productoId(producto.getId())
                                .nombreProducto(producto.getNombre())
                                .sucursalId(sucursal.getId())
                                .nombreSucursal(sucursal.getNombre())
                                .stock(producto.getStock())
                                .build())
                        .defaultIfEmpty(null))
                .filter(dto -> dto != null);
    }

    private Producto toDomain(ProductoEntity entity) {
        return Producto.builder().id(entity.getId()).sucursalId(entity.getSucursalId()).nombre(entity.getNombre()).stock(entity.getStock()).build();
    }
}
