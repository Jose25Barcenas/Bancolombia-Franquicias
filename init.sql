CREATE TABLE IF NOT EXISTS franquicias (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS sucursales (
    id SERIAL PRIMARY KEY,
    franquicia_id BIGINT NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    FOREIGN KEY (franquicia_id) REFERENCES franquicias(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS productos (
    id SERIAL PRIMARY KEY,
    sucursal_id BIGINT NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0,
    FOREIGN KEY (sucursal_id) REFERENCES sucursales(id) ON DELETE CASCADE
);

CREATE INDEX idx_sucursales_franquicia ON sucursales(franquicia_id);
CREATE INDEX idx_productos_sucursal ON productos(sucursal_id);
