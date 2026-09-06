CREATE DATABASE IF NOT EXISTS inventario_apf1
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE inventario_apf1;

CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(80) NOT NULL,
    correo VARCHAR(120) NOT NULL UNIQUE,
    clave VARCHAR(120) NOT NULL,
    rol ENUM('ADMIN', 'ENCARGADO') NOT NULL DEFAULT 'ENCARGADO',
    estado TINYINT(1) NOT NULL DEFAULT 1
);

CREATE TABLE categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL UNIQUE,
    descripcion VARCHAR(180)
);

CREATE TABLE productos (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    id_categoria INT NOT NULL,
    nombre VARCHAR(120) NOT NULL,
    descripcion VARCHAR(200),
    unidad_medida VARCHAR(30) NOT NULL DEFAULT 'unidad',
    stock_actual INT NOT NULL DEFAULT 0,
    stock_minimo INT NOT NULL DEFAULT 5,
    estado TINYINT(1) NOT NULL DEFAULT 1,
    CONSTRAINT fk_productos_categorias
        FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria)
);

CREATE TABLE movimientos (
    id_movimiento INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT NOT NULL,
    id_usuario INT NOT NULL,
    tipo ENUM('ENTRADA', 'SALIDA') NOT NULL,
    cantidad INT NOT NULL,
    fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    observacion VARCHAR(200),
    CONSTRAINT fk_movimientos_productos
        FOREIGN KEY (id_producto) REFERENCES productos(id_producto),
    CONSTRAINT fk_movimientos_usuarios
        FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

INSERT INTO usuarios (nombres, correo, clave, rol) VALUES
('Administrador', 'admin@utp.edu.pe', '123456', 'ADMIN'),
('Encargado de Almacén', 'almacen@utp.edu.pe', '123456', 'ENCARGADO');

INSERT INTO categorias (nombre, descripcion) VALUES
('Bebidas', 'Productos líquidos para venta'),
('Insumos', 'Materiales e insumos internos'),
('Limpieza', 'Artículos de limpieza del negocio');

INSERT INTO productos (id_categoria, nombre, descripcion, unidad_medida, stock_actual, stock_minimo) VALUES
(1, 'Agua mineral 500ml', 'Botella individual', 'unidad', 40, 10),
(1, 'Gaseosa personal', 'Bebida embotellada', 'unidad', 18, 8),
(2, 'Vasos descartables', 'Paquete de vasos', 'paquete', 7, 10),
(3, 'Papel toalla', 'Rollo de papel', 'unidad', 4, 6);
