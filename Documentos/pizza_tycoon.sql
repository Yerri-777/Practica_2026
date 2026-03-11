DROP DATABASE IF EXISTS pizza_tycoon;
CREATE DATABASE pizza_tycoon;
USE pizza_tycoon;

-- ====================================
-- TABLA ROLES
-- ====================================

CREATE TABLE rol (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(100)
);

INSERT INTO rol(nombre,descripcion) VALUES
('ADMIN','Super administrador del sistema'),
('ADMIN_TIENDA','Administrador de sucursal'),
('JUGADOR','Jugador del videojuego');

-- ====================================
-- TABLA SUCURSALES
-- ====================================

CREATE TABLE sucursal (
    id_sucursal INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(150),
    estado BOOLEAN DEFAULT TRUE
);

INSERT INTO sucursal(nombre,direccion,estado) VALUES
('Sucursal Centro','Avenida Central 123',true),
('Sucursal Norte','Calle Norte 45',true);

-- ====================================
-- TABLA USUARIOS
-- ====================================

CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nombre_completo VARCHAR(100),
    estado BOOLEAN DEFAULT TRUE,
    id_rol INT,
    id_sucursal INT,

    FOREIGN KEY (id_rol) REFERENCES rol(id_rol),
    FOREIGN KEY (id_sucursal) REFERENCES sucursal(id_sucursal)
);

INSERT INTO usuario(username,password,nombre_completo,estado,id_rol,id_sucursal)
VALUES
('admin','admin123','Super Administrador',true,1,1),
('admin_tienda','1234','Administrador Tienda',true,2,1),
('jugador','1234','Jugador Demo',true,3,1);


-- ====================================
-- TABLA CLIENTES
-- ====================================

CREATE TABLE cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    telefono VARCHAR(20),
    direccion VARCHAR(150)
);

INSERT INTO cliente(nombre,telefono,direccion) VALUES
('Carlos Ramirez','5551111','Zona Centro'),
('Ana Torres','5552222','Barrio Norte'),
('Luis Martinez','5553333','Zona Sur');

-- ====================================
-- TABLA PRODUCTOS
-- ====================================

CREATE TABLE producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    descripcion VARCHAR(150),
    precio DECIMAL(10,2),
    activo BOOLEAN,
    id_sucursal INT,

    FOREIGN KEY(id_sucursal) REFERENCES sucursal(id_sucursal)
);

INSERT INTO producto(nombre,descripcion,precio,activo,id_sucursal)
VALUES
('Pizza Pepperoni','Pizza clásica',10,true,1),
('Pizza Hawaiana','Pizza con piña',11,true,1),
('Pizza Vegetariana','Pizza de vegetales',9.5,true,1),
('Coca Cola','Bebida gaseosa',2,true,1);

-- ====================================
-- TABLA INGREDIENTES
-- ====================================

CREATE TABLE ingrediente (
    id_ingrediente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    unidad VARCHAR(20)
);

INSERT INTO ingrediente(nombre,unidad) VALUES
('Masa','unidad'),
('Queso','gramos'),
('Salsa Tomate','gramos'),
('Pepperoni','gramos'),
('Piña','gramos'),
('Vegetales','gramos');

-- ====================================
-- TABLA RECETAS
-- ====================================

CREATE TABLE receta (
    id_receta INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT,
    id_ingrediente INT,
    cantidad DECIMAL(10,2),

    FOREIGN KEY (id_producto) REFERENCES producto(id_producto),
    FOREIGN KEY (id_ingrediente) REFERENCES ingrediente(id_ingrediente)
);

INSERT INTO receta(id_producto,id_ingrediente,cantidad) VALUES
(1,1,1),(1,2,150),(1,3,50),(1,4,80),
(2,1,1),(2,2,150),(2,3,50),(2,5,70),
(3,1,1),(3,2,120),(3,3,50),(3,6,100);

-- ====================================
-- TABLA INVENTARIO
-- ====================================

CREATE TABLE inventario (
    id_inventario INT AUTO_INCREMENT PRIMARY KEY,
    id_sucursal INT,
    id_ingrediente INT,
    cantidad DECIMAL(10,2),

    FOREIGN KEY (id_sucursal) REFERENCES sucursal(id_sucursal),
    FOREIGN KEY (id_ingrediente) REFERENCES ingrediente(id_ingrediente)
);

INSERT INTO inventario(id_sucursal,id_ingrediente,cantidad) VALUES
(1,1,50),
(1,2,10000),
(1,3,5000),
(1,4,3000),
(1,5,2000),
(1,6,2000);

-- ====================================
-- TABLA PARTIDA
-- ====================================

CREATE TABLE partida (
    id_partida INT AUTO_INCREMENT PRIMARY KEY,
    fecha_inicio DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_fin DATETIME,
    puntaje_final INT,
    nivel_alcanzado INT,

    pedidos_correctos INT DEFAULT 0,
    pedidos_cancelados INT DEFAULT 0,
    pedidos_tarde INT DEFAULT 0,

    id_usuario INT,
    id_sucursal INT,

    FOREIGN KEY(id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY(id_sucursal) REFERENCES sucursal(id_sucursal)
);

-- ====================================
-- TABLA PEDIDO
-- ====================================

CREATE TABLE pedido (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    tiempo_limite INT,
    tiempo_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado_actual VARCHAR(50),

    id_producto INT,
    id_partida INT,

    FOREIGN KEY(id_producto) REFERENCES producto(id_producto),
    FOREIGN KEY(id_partida) REFERENCES partida(id_partida)
);

-- ====================================
-- HISTORIAL DE ESTADOS DE PEDIDO
-- ====================================

CREATE TABLE historial_estado_pedido(

    id_historial INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT,
    estado VARCHAR(50),
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY(id_pedido) REFERENCES pedido(id_pedido)

);

-- ====================================
-- DETALLE PEDIDO
-- ====================================

CREATE TABLE detalle_pedido (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT,
    id_producto INT,
    cantidad INT,
    precio DECIMAL(10,2),

    FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido),
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

-- ====================================
-- CONFIGURACION DE NIVELES
-- ====================================

CREATE TABLE nivel_configuracion (
    id_nivel INT AUTO_INCREMENT PRIMARY KEY,
    nivel INT NOT NULL,
    tiempo_base_segundos INT,
    pedidos_para_subir INT,
    puntos_para_subir INT
);

INSERT INTO nivel_configuracion
(nivel,tiempo_base_segundos,pedidos_para_subir,puntos_para_subir)
VALUES
(1,60,5,100),
(2,50,8,200),
(3,40,12,400),
(4,35,15,700),
(5,30,20,1000);