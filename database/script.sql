-- CREACIÓN DE BASE DE DATOS
CREATE DATABASE restaurante_bd;

-- CREACIÓN DE TABLAS
CREATE TABLE usuarios(
id_usuario SERIAL PRIMARY KEY,
nombre VARCHAR(255) NOT NULL,
correo  VARCHAR(255) UNIQUE NOT NULL,
contrasena VARCHAR(255) NOT NULL,
rol VARCHAR(255) NOT NULL
);


CREATE TABLE productos(
id_producto SERIAL PRIMARY KEY,
nombre VARCHAR (255) NOT NULL,
precio NUMERIC NOT NULL CHECK (precio> 0),
categoria VARCHAR (255) NOT NULL
);

CREATE TABLE clientes(
id_cliente SERIAL PRIMARY KEY,
nombre VARCHAR (255) NOT NULL,
telefono VARCHAR(255) NOT NULL
);

CREATE TABLE pedidos(
id_pedido SERIAL PRIMARY KEY,
fecha DATE NOT NULL,
total NUMERIC NOT NULL CHECK (total> 0) ,
id_cliente INTEGER NOT NULL,

FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente) 
);

CREATE TABLE detalle_pedido (
id_detalle SERIAL PRIMARY KEY,
cantidad INTEGER NOT NULL CHECK (cantidad> 0) ,
id_pedido INTEGER NOT NULL,
id_producto INTEGER NOT NULL,

FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido),
FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
);

-- INSERTS DE PRUEBA
INSERT INTO usuarios (nombre,correo,contrasena,rol) VALUES('Carlos Admin','admin@restaurante.com',123456,'Administrador');
INSERT INTO usuarios (nombre,correo,contrasena,rol) VALUES('Laura Caja','caja@restaurante.com',123456,'Cajero');

SELECT * FROM usuarios;

INSERT INTO productos (nombre,precio,categoria) VALUES('Hamburguesa',18000,'Comida');
INSERT INTO productos (nombre,precio,categoria) VALUES('Pizza Personal',220000,'Comida');
INSERT INTO productos (nombre,precio,categoria) VALUES('Gaseosa',5000,'Bebida');
INSERT INTO productos (nombre,precio,categoria) VALUES('Jugo Natural',7000,'Bebida');

SELECT * FROM productos;

INSERT INTO clientes (nombre,telefono) VALUES('Juan Perez',3001234567);
INSERT INTO clientes (nombre,telefono) VALUES('Maria Gomez',301987465);

SELECT * FROM clientes;

INSERT INTO pedidos (fecha,total,id_cliente) VALUES ('18/04/2026',23000,1);
INSERT INTO pedidos (fecha,total,id_cliente) VALUES ('18/04/2026',29000,2);

SELECT  FROM pedidos;

INSERT INTO detalle_pedido (cantidad,id_pedido,id_producto) VALUES (1,1,1);
INSERT INTO detalle_pedido (cantidad,id_pedido,id_producto) VALUES (1,1,3);
INSERT INTO detalle_pedido (cantidad,id_pedido,id_producto) VALUES (1,2,2);
INSERT INTO detalle_pedido (cantidad,id_pedido,id_producto) VALUES (1,2,4);

SELECT * FROM detalle_pedido;




