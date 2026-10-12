-- NOVA LIMA APF2. Compatible con las cuatro tablas de APF1.
-- Si partes de cero, ejecuta completo. Si migras APF1, se conservan las filas;
-- solo se convierten las credenciales de DEMOSTRACIÓN cuyo valor aún sea '123456'.
-- No ejecutes este script de datos de ejemplo sobre una BD real de producción.
CREATE DATABASE IF NOT EXISTS inventario_apf1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE inventario_apf1;
CREATE TABLE IF NOT EXISTS usuarios (
 id_usuario INT AUTO_INCREMENT PRIMARY KEY,
 nombres VARCHAR(80) NOT NULL,
 correo VARCHAR(120) NOT NULL UNIQUE,
 clave VARCHAR(120) NOT NULL,
 rol ENUM('ADMIN','ENCARGADO') NOT NULL DEFAULT 'ENCARGADO',
 estado TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS categorias (
 id_categoria INT AUTO_INCREMENT PRIMARY KEY,
 nombre VARCHAR(80) NOT NULL UNIQUE,
 descripcion VARCHAR(180)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS productos (
 id_producto INT AUTO_INCREMENT PRIMARY KEY,
 id_categoria INT NOT NULL,
 nombre VARCHAR(120) NOT NULL,
 descripcion VARCHAR(200),
 unidad_medida VARCHAR(30) NOT NULL DEFAULT 'unidad',
 stock_actual INT NOT NULL DEFAULT 0,
 stock_minimo INT NOT NULL DEFAULT 5,
 estado TINYINT(1) NOT NULL DEFAULT 1,
 CONSTRAINT fk_productos_categorias FOREIGN KEY(id_categoria) REFERENCES categorias(id_categoria)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS movimientos (
 id_movimiento INT AUTO_INCREMENT PRIMARY KEY,
 id_producto INT NOT NULL,
 id_usuario INT NOT NULL,
 tipo ENUM('ENTRADA','SALIDA') NOT NULL,
 cantidad INT NOT NULL,
 fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 observacion VARCHAR(200),
 CONSTRAINT fk_movimientos_productos FOREIGN KEY(id_producto) REFERENCES productos(id_producto),
 CONSTRAINT fk_movimientos_usuarios FOREIGN KEY(id_usuario) REFERENCES usuarios(id_usuario)
) ENGINE=InnoDB;

INSERT INTO usuarios (nombres,correo,clave,rol) VALUES
 ('Administrador','admin@utp.edu.pe','pbkdf2:sha256:150000:aLvqrib8VSj/KVPHTUUPNA==:ESI/bADeFYr37km0mMxfSZ01hpIeII8Jafi/Y+Kr/PU=','ADMIN'),
 ('Encargado de Almacén','almacen@utp.edu.pe','pbkdf2:sha256:150000:elH1txV6REJB1AmFXbyr5Q==:W2koB+qJCNs3UFIbN1TYW36x8Wgcb+1+wtk41eTklMU=','ENCARGADO')
ON DUPLICATE KEY UPDATE correo=VALUES(correo);

-- APF1 almacenaba '123456'; migración acotada a las dos cuentas de práctica.
UPDATE usuarios SET clave='pbkdf2:sha256:150000:aLvqrib8VSj/KVPHTUUPNA==:ESI/bADeFYr37km0mMxfSZ01hpIeII8Jafi/Y+Kr/PU=' WHERE correo='admin@utp.edu.pe' AND clave='123456';
UPDATE usuarios SET clave='pbkdf2:sha256:150000:elH1txV6REJB1AmFXbyr5Q==:W2koB+qJCNs3UFIbN1TYW36x8Wgcb+1+wtk41eTklMU=' WHERE correo='almacen@utp.edu.pe' AND clave='123456';

INSERT INTO categorias(nombre,descripcion) VALUES
 ('Bebidas','Productos líquidos para venta'),
 ('Insumos','Materiales e insumos internos'),
 ('Limpieza','Artículos de limpieza del negocio')
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

-- Los productos de ejemplo solo se cargan cuando la tabla está vacía.
INSERT INTO productos(id_categoria,nombre,descripcion,unidad_medida,stock_actual,stock_minimo)
SELECT c.id_categoria,'Agua mineral 500ml','Botella individual','unidad',40,10
FROM categorias c WHERE c.nombre='Bebidas' AND NOT EXISTS (SELECT 1 FROM productos);
INSERT INTO productos(id_categoria,nombre,descripcion,unidad_medida,stock_actual,stock_minimo)
SELECT c.id_categoria,'Gaseosa personal','Bebida embotellada','unidad',18,8
FROM categorias c WHERE c.nombre='Bebidas' AND NOT EXISTS (SELECT 1 FROM productos WHERE nombre='Gaseosa personal');
INSERT INTO productos(id_categoria,nombre,descripcion,unidad_medida,stock_actual,stock_minimo)
SELECT c.id_categoria,'Vasos descartables','Paquete de vasos','paquete',7,10
FROM categorias c WHERE c.nombre='Insumos' AND NOT EXISTS (SELECT 1 FROM productos WHERE nombre='Vasos descartables');
INSERT INTO productos(id_categoria,nombre,descripcion,unidad_medida,stock_actual,stock_minimo)
SELECT c.id_categoria,'Papel toalla','Rollo de papel','unidad',4,6
FROM categorias c WHERE c.nombre='Limpieza' AND NOT EXISTS (SELECT 1 FROM productos WHERE nombre='Papel toalla');
